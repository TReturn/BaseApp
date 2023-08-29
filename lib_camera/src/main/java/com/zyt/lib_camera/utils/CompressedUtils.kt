package com.zyt.lib_camera.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import com.blankj.utilcode.util.PathUtils
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
    // 时间戳，用于给图片命令防止重复
    private val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

    /**
     *
     * @param context Context
     * @param bitmap Bitmap
     * @return String
     */
    suspend fun compressed(context: Context, bitmap: Bitmap):String{
        val compressedImageFile =
            Compressor.compress(context, saveBitmapFile(bitmap))
        Logger.d("压缩路径: ${compressedImageFile.absolutePath}")
        return compressedImageFile.absolutePath
    }

    /**
     * 压缩路径存到应用缓存目录
     * @param bitmap Bitmap
     * @return File
     */
    private fun saveBitmapFile(bitmap: Bitmap): File {
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.CHINA)
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

}