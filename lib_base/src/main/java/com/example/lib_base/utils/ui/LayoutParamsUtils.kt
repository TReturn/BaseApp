package com.example.lib_base.utils.ui

import android.view.View

/**
 * @CreateDate : 2021/8/27 23:32
 * @Author : 青柠
 * @Description : LayoutParams动态设置View宽高
 */
object LayoutParamsUtils {

    /**
     * 设置View高度
     * @param view View
     * @param height Int
     */
    fun setHeight(view: View, height: Int) {
        val params = view.layoutParams
        params.height = height
        view.layoutParams = params
    }

    /**
     * 设置View宽度
     * @param view View
     * @param width Int
     */
    fun setWidth(view: View, width: Int) {
        val params = view.layoutParams
        params.width = width
        view.layoutParams = params
    }

    /**
     * 设置View宽高
     * @param view View
     * @param height Int
     * @param width Int
     */
    fun setHeightAndWidth(view: View, height: Int, width: Int) {
        val params = view.layoutParams
        params.height = height
        params.width = width
        view.layoutParams = params
    }
}