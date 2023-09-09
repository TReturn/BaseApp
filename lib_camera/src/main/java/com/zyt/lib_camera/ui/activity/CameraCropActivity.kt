package com.zyt.lib_camera.ui.activity

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraInfo
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.TorchState
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.utils.qmui.QMUIStatusBarHelper
import com.hjq.toast.Toaster
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.orhanobut.logger.Logger
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropActivity
import com.zyt.lib_camera.databinding.ActivityCameraCropBinding
import com.zyt.lib_camera.utils.CompressedUtils
import com.zyt.lib_camera.utils.CreateFileSuffixUtils
import com.zyt.lib_camera.utils.GlideEngine
import com.zyt.lib_camera.viewmodel.CameraCropViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/**
 * @CreateDate : 2023/8/29
 * @Author : 青柠
 * @Description :提供CameraX拍照能力,提供原始URI和压缩后的路径返回
 */
class CameraCropActivity : BaseActivity<CameraCropViewModel, ActivityCameraCropBinding>() {

    // 相片拍摄器
    private var imageCapture: ImageCapture? = null

    // 相机执行器
    private lateinit var cameraExecutor: ExecutorService

    // 指定用于预览的相机，默认为后置相机，如果需要前置相机预览请使用CameraSelector.DEFAULT_FRONT_CAMERA
    private var cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private lateinit var cameraInfo: CameraInfo
    private lateinit var cameraControl: CameraControl

    private lateinit var uCropLauncher: ActivityResultLauncher<Intent>

    //裁剪后的路径
    private var cropPath = ""

    //裁剪后的文件名
    private var cropName = ""

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
        QMUIStatusBarHelper.translucent(this)
        setTranslucent(mDatabind.flTranslucent)

        //传参可直接打开图库
        if (intent.getBooleanExtra("IS_OPEN_GALLERY", false)) {
            ProxyClick().toGallery()
        }

        startCamera()

        // 实例化相机执行器，用于或许相机资源
        cameraExecutor = Executors.newSingleThreadExecutor()

        uCropLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                    it.data?.let { success ->
                        runBlocking {
                            val uri: String = UCrop.getOutput(success).toString()
                            val context = this@CameraCropActivity
                            context.setResult(Activity.RESULT_OK, Intent().apply {
                                putExtra("RESULT", Bundle().apply {
                                    putString("PICTURE_URI", uri)
                                    val bitmap = CompressedUtils.uriToBitmap(context, uri)
                                    val compressedPath =
                                        async { CompressedUtils.compressed(context, bitmap) }
                                    putString("PICTURE_PATH", compressedPath.await())
                                    putString("PICTURE_NAME", CompressedUtils.getPicName())
                                })
                            })
                            context.finish()
                            Logger.d("Photo capture succeeded: $uri")
                        }
                    }

                } else {
                    it.data?.let { error ->
                        Toaster.show(UCrop.getError(error))
                    }

                }

            }
    }

    /**
     * 拍照
     */
    private fun takePhoto() {
        // 校验是否有可用的相机拍摄器
        val imageCapture = imageCapture ?: return

        // 使用MediaStore操作相片文件
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, CreateFileSuffixUtils.name())
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        // 指定输出参数
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        // 开始拍摄相片
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    // 拍摄失败
                    val msg = "Photo capture failed: ${exc.message}"
                    Logger.d(msg)
                    Toaster.show(msg)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    // 拍摄成功，saveUri就是图片的uri地址
                    val uri = output.savedUri.toString()
                    intentData(uri)
                }
            }
        )
    }

    /**
     * 打开相机
     */
    private fun startCamera() {
        // 获取相机过程提供者实例
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        // 为相机过程提供者添加监听
        cameraProviderFuture.addListener({

            // 获取具体的相机过程提供者
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            // 创建相机预览窗口
            val preview = Preview.Builder()
                .build()
                .also {
                    // 这里通过我们布局中的Preview进行预览
                    it.setSurfaceProvider(mDatabind.viewPreview.surfaceProvider)
                }

            // 获取用于拍照的实例
            imageCapture = ImageCapture.Builder()
                //设置图像宽高比
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .build()

            try {
                // 绑定提供者之前先进行解绑，不能重复绑定
                cameraProvider.unbindAll()

                // 绑定提供者,将相机的生命周期进行绑定，因为camerax具有生命周期感知力，所以消除打开和关闭相机的任务
                val camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
                //用来聚焦、手势、闪光灯、手电等操作
                cameraInfo = camera.cameraInfo
                cameraControl = camera.cameraControl
            } catch (e: Exception) {
                Logger.d("相机绑定异常 ${e.message}")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    /**
     * 跳转返回数据，通过await获取压缩耗时任务后的结果
     * @param uri String
     */
    private fun intentData(uri: String) {
        mViewModel.isButtonClickable.value = true
        //裁剪
        cropRawPhoto(uri.toUri())
    }

    /**
     * 使用UCrop进行图片剪裁
     *
     * @param uri
     */
    private fun cropRawPhoto(uri: Uri) {
        val options = UCrop.Options().apply {
            // 修改标题栏颜色
            //setToolbarColor(Color.parseColor("#FFFFFF"))
            // 修改状态栏颜色
            setStatusBarColor(Color.parseColor("#000000"))
            // 隐藏底部工具
            setHideBottomControls(false)
            // 图片格式
            setCompressionFormat(Bitmap.CompressFormat.JPEG)
            // 设置图片压缩质量
            //setCompressionQuality(100)
            // 是否让用户调整范围(默认false)，如果开启，可能会造成剪切的图片的长宽比不是设定的
            // 如果不开启，用户不能拖动选框，只能缩放图片
            setFreeStyleCropEnabled(true)
            // 圆
            setCircleDimmedLayer(false)
            // 不显示网格线
            setShowCropGrid(false)
        }
        //存储路径
        val file = CreateFileSuffixUtils.file(".jpeg")
        cropPath = file.absolutePath
        cropName = file.name

        // 设置源uri及目标uri
        val intent = Intent(this, UCropActivity::class.java)
        val cropOptionsBundle = Bundle()
        cropOptionsBundle.putParcelable(UCrop.EXTRA_INPUT_URI, uri)
        cropOptionsBundle.putParcelable(UCrop.EXTRA_OUTPUT_URI, Uri.fromFile(file))
        cropOptionsBundle.putAll(options.optionBundle)
        intent.putExtras(cropOptionsBundle)
        uCropLauncher.launch(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 页面销毁时关闭相机执行器
        cameraExecutor.shutdown()
    }

    inner class ProxyClick {
        fun toTakePhoto() {
            //拍照
            takePhoto()
            mViewModel.isButtonClickable.value = false
        }

        fun toFlashLight() {
            if (cameraInfo.torchState.value == TorchState.ON) {
                cameraControl.enableTorch(false)
            } else {
                cameraControl.enableTorch(true)
            }
        }

        fun toOverTurn() {
            //翻转
            cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                CameraSelector.DEFAULT_FRONT_CAMERA
            } else {
                CameraSelector.DEFAULT_BACK_CAMERA
            }
            startCamera()
        }

        fun toGallery() {
            //图库
            PictureSelector.create(this@CameraCropActivity)
                .openGallery(SelectMimeType.ofImage())
                .setImageEngine(GlideEngine.createGlideEngine())
                .isDisplayCamera(false)
                .setSelectionMode(SelectModeConfig.SINGLE)
                .forResult(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: ArrayList<LocalMedia>) {
                        intentData(result[0].path)
                    }

                    override fun onCancel() {}
                })
        }

        fun toFinish() {
            finish()
        }
    }
}