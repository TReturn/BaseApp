package com.example.lib_base.utils.calculate

import com.example.lib_base.BaseApplication


/**
 * @CreateDate : 2021/7/30
 * @Author : 青柠
 * @Description : px跟dp互相转换
 */
object DensityUtils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    @JvmStatic
    fun dip2px(dpValue: Float): Int {
        val scale = BaseApplication.context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(pxValue: Float): Int {
        val scale = BaseApplication.context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }
}