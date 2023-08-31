package com.zyt.lib_camera.utils

import android.content.Context
import android.graphics.Bitmap
import com.blankj.utilcode.util.PathUtils
import com.example.lib_base.utils.time.TimeUtils
import com.orhanobut.logger.Logger
import id.zelory.compressor.Compressor
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

    /**
     *
     * @param context Context
     * @param bitmap Bitmap
     * @return String
     */
    suspend fun compressed(context: Context, bitmap: Bitmap): String {
        val file = saveBitmapFile(bitmap)
        val compressedImageFile = Compressor.compress(context, file)
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
        // 时间戳，用于给图片命令防止重复
        val timeFormat = TimeUtils.dateFormatYMDHMS
        val name = SimpleDateFormat(timeFormat, Locale.CHINA)
            .format(System.currentTimeMillis())
        //将要保存图片的路径(默认内存应用缓存路径)
        val file = File("${PathUtils.getInternalAppCachePath()} ${name}.jpeg")

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

}