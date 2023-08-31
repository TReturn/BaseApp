package com.zyt.lib_camera.ui.activity

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
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
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.utils.qmui.QMUIStatusBarHelper
import com.example.lib_base.utils.time.TimeUtils
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.hjq.toast.Toaster
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.orhanobut.logger.Logger
import com.zyt.lib_camera.databinding.ActivityCameraBinding
import com.zyt.lib_camera.utils.GlideEngine
import com.zyt.lib_camera.viewmodel.CameraViewModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/**
 * @CreateDate : 2023/8/29
 * @Author : 青柠
 * @Description :提供CameraX拍照能力
 */
class CameraActivity : BaseActivity<CameraViewModel, ActivityCameraBinding>() {

    // 相片拍摄器
    private var imageCapture: ImageCapture? = null

    // 相机执行器
    private lateinit var cameraExecutor: ExecutorService

    // 指定用于预览的相机，默认为后置相机，如果需要前置相机预览请使用CameraSelector.DEFAULT_FRONT_CAMERA
    private var cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private lateinit var cameraInfo: CameraInfo
    private lateinit var cameraControl: CameraControl

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
        QMUIStatusBarHelper.translucent(this)
        setTranslucent(mDatabind.flTranslucent)

        mDatabind.include.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                finish()
            }
        })

        //传参可直接打开图库
        if (intent.getBooleanExtra("IS_OPEN_GALLERY", false)) {
            ProxyClick().toGallery()
        }

        startCamera()

        // 实例化相机执行器，用于或许相机资源
        cameraExecutor = Executors.newSingleThreadExecutor()

    }

    /**
     * 拍照
     */
    private fun takePhoto() {
        // 校验是否有可用的相机拍摄器
        val imageCapture = imageCapture ?: return
        // 时间戳，用于给图片命令防止重复
        val timeFormat = TimeUtils.dateFormatYMDHMS
        // 定义拍摄相片名称
        val name = SimpleDateFormat(timeFormat, Locale.CHINA)
            .format(System.currentTimeMillis())

        // 使用MediaStore操作相片文件
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
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
                    val intent = Intent()
                    val bundle = Bundle()
                    bundle.putString("PICTURE_URI", uri)
                    intent.putExtra("RESULT", bundle)
                    this@CameraActivity.setResult(Activity.RESULT_OK, intent)
                    this@CameraActivity.finish()
                    Logger.d("拍摄路径: ${output.savedUri}")
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
                //设置图像质量
                //.setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                //.setJpegQuality(70)
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

    override fun onDestroy() {
        super.onDestroy()
        // 页面销毁时关闭相机执行器
        cameraExecutor.shutdown()
    }

    inner class ProxyClick {
        fun toTakePhoto() {
            //拍照
            takePhoto()
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
            PictureSelector.create(this@CameraActivity)
                .openGallery(SelectMimeType.ofImage())
                .setImageEngine(GlideEngine.createGlideEngine())
                .isDisplayCamera(false)
                .setSelectionMode(SelectModeConfig.SINGLE)
                .forResult(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: ArrayList<LocalMedia>) {
                        val path = result[0].path
                        val intent = Intent()
                        val bundle = Bundle()
                        bundle.putString("PICTURE_URI", path)
                        intent.putExtra("RESULT", bundle)
                        this@CameraActivity.setResult(Activity.RESULT_OK, intent)
                        this@CameraActivity.finish()
                        Logger.d("Photo capture succeeded: ${path}")
                    }

                    override fun onCancel() {}
                })
        }
    }
}