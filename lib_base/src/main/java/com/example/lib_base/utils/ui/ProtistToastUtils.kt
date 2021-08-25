package com.example.lib_base.utils.ui

import android.content.Context
import android.widget.Toast
import com.example.lib_base.BaseApplication

/**
 * @CreateDate: 2020/11/4
 * @Author: 青柠
 * @Description: 原生Toast封装
 */
object ProtistToastUtils {

    fun show(msg: String) {
        Toast.makeText(
            BaseApplication.context,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun showLong(msg: String) {
        Toast.makeText(
            BaseApplication.context,
            msg,
            Toast.LENGTH_LONG
        ).show()
    }

}