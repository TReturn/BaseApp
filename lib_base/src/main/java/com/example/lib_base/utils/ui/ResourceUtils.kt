package com.example.lib_base.utils.ui

import com.example.lib_base.BaseApplication

/**
 * @CreateDate : 2021/8/17 1:32
 * @Author : 青柠
 * @Description : 获取资源
 */
object ResourceUtils {
    fun getString(resId: Int): String {
        return BaseApplication.context.getString(resId)
    }
}