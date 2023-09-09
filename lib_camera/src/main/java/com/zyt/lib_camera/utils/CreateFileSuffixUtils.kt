package com.zyt.lib_camera.utils

import com.blankj.utilcode.util.PathUtils
import com.example.lib_base.utils.time.TimeUtils
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * @CreateDate: 2023/9/8 11:20
 * @Author: 青柠
 * @Description:
 */
object CreateFileSuffixUtils {

    /**
     * 获取一个存储的路径File
     * @param suffix String 文件后缀名，如.jpeg
     * @return File
     */
    fun file(suffix: String): File {
        val timeFormat = TimeUtils.dateFormatYMDHMS
        val name = SimpleDateFormat(timeFormat, Locale.CHINA)
            .format(System.currentTimeMillis())
        //将要保存图片的路径(默认内存应用缓存路径)
        return File("${PathUtils.getInternalAppCachePath()} ${name}$suffix")
    }

    /**
     * 获取一个存储的文件名
     * @return String
     */
    fun name(): String {
        val timeFormat = TimeUtils.dateFormatYMDHMS
        return SimpleDateFormat(timeFormat, Locale.CHINA)
            .format(System.currentTimeMillis())
    }
}