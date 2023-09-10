package com.example.lib_base.ext

import com.example.lib_base.BaseApplication

/**
 * @CreateDate: 2023/9/10 11:52
 * @Author: 青柠
 * @Description: 像素转换拓展函数
 */

/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun Float.dpToPx(): Float {
    val scale = BaseApplication.context.resources.displayMetrics.density
    return (this * scale + 0.5f)
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun Float.pxToDp(): Float {
    val scale = BaseApplication.context.resources.displayMetrics.density
    return (this / scale + 0.5f)
}

fun Float.spToPx(): Float {
    val scale = BaseApplication.context.resources.displayMetrics.density
    return (this * scale + 0.5f)
}