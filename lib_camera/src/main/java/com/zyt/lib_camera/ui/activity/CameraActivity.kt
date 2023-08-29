package com.zyt.lib_camera.ui.activity

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.utils.qmui.QMUIStatusBarHelper
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.hjq.toast.Toaster
import com.orhanobut.logger.Logger
import com.zyt.lib_camera.databinding.ActivityCameraBinding
import com.zyt.lib_camera.viewmodel.CameraViewModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * @CreateDate : 2023/8/29
 * @Author : 青柠
 * @Description :
 */
class CameraActivity : BaseActivity<CameraViewModel, ActivityCameraBinding>() {

    // 时间戳，用于给图片命令防止重复
    private val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

    // 相片拍摄器
    private var imageCapture: ImageCapture? = null

    // 相机执行器
    private lateinit var cameraExecutor: ExecutorService

    // 指定用于预览的相机，默认为后置相机，如果需要前置相机预览请使用CameraSelector.DEFAULT_FRONT_CAMERA
    private var cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

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
        // 定义拍摄相片名称
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.CHINA)
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
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    val intent = Intent()
                    val bundle = Bundle()
                    bundle.putString("PICTURE_URI", output.savedUri.toString())
                    intent.putExtra("RESULT", bundle)
                    this@CameraActivity.setResult(Activity.RESULT_OK, intent)
                    this@CameraActivity.finish()
                    Logger.d(msg)
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
                .build()

            try {
                // 绑定提供者之前先进行解绑，不能重复绑定
                cameraProvider.unbindAll()

                // 绑定提供者,将相机的生命周期进行绑定，因为camerax具有生命周期感知力，所以消除打开和关闭相机的任务
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

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

        }
    }
}