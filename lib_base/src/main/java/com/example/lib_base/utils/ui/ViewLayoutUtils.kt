package com.example.lib_base.utils.ui

import android.view.View

/**
 * @CreateDate : 2021/8/27 23:32
 * @Author : 青柠
 * @Description : 动态设置View宽高
 */
object ViewLayoutUtils {
    fun setHeight(view: View, height: Int) {
        val params = view.layoutParams
        params.height = height
        view.layoutParams = params
    }

    fun setWidth(view: View, width: Int) {
        val params = view.layoutParams
        params.width = width
        view.layoutParams = params
    }

    fun setHeightAndWidth(view: View, height: Int, width: Int) {
        val params = view.layoutParams
        params.height = height
        params.width = width
        view.layoutParams = params
    }
}