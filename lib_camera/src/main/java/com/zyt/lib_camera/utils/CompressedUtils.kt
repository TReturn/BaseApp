package com.zyt.lib_camera.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.blankj.utilcode.util.PathUtils
import com.example.lib_base.utils.time.TimeUtils
import com.orhanobut.logger.Logger
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.size
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * @CreateDate: 2023/8/29 23:34
 * @Author: 青柠
 * @Description: 可将图片File压缩
 */
object CompressedUtils {

    //原图大小
    private var originalSize = ""

    //压缩后大小
    private var compressSize = ""

    //图片名称
    private var picName = ""

    /**
     *  URI转Bitmap
     * @param context Context
     * @param uri String
     * @return Bitmap
     */
    fun uriToBitmap(context: Context, uri: String): Bitmap {

        // 这里获取到对应相片，如果用于显示，建议进行相应压缩处理
        return if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(uri))
        } else {
            val source = ImageDecoder.createSource(
                context.contentResolver,
                Uri.parse(uri)
            )
            ImageDecoder.decodeBitmap(source)
        }

    }

    /**
     * 开始压缩
     * @param context Context
     * @param bitmap Bitmap
     * @return String
     */
    suspend fun compressed(context: Context, bitmap: Bitmap): String {
        val file = saveBitmapFile(bitmap)
        val compressedImageFile = Compressor.compress(context, file) {
            size(5_242_88) // 500KB
        }
        originalSize = "原图大小：${file.length() / 1024}KB"
        compressSize = "压缩后大小：${File(compressedImageFile.absolutePath).length() / 1024}KB"
        Logger.d("压缩路径: ${compressedImageFile.absolutePath},$originalSize,$compressSize")

        return compressedImageFile.absolutePath
    }

    /**
     * 压缩路径存到应用缓存目录
     * @param bitmap Bitmap
     * @return File
     */
    private fun saveBitmapFile(bitmap: Bitmap): File {
        //将要保存图片的路径(默认内存应用缓存路径)
        val file = CreateFileSuffixUtils.file(".jpeg")

        try {
            val bos = BufferedOutputStream(FileOutputStream(file))
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            bos.flush()
            bos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

    /**
     * 获取原图大小
     */
    fun getOriginalSize(): String {
        return originalSize
    }

    /**
     * 获取压缩图大小
     */
    fun getCompressSize(): String {
        return compressSize
    }

    fun getPicName(): String {
        return picName
    }

}