package com.example.lib_base.utils.ui

import android.content.res.AssetManager
import android.graphics.Typeface
import android.widget.Button
import android.widget.TextView
import com.example.lib_base.BaseApplication

/**
 * @CreateDate: 2020/11/23
 * @Author: 青柠
 * @Description: 改变TextView字体
 */

object TextFontUtils {

    //从asset 读取字体
    //得到AssetManager
    private val mgr: AssetManager = BaseApplication.context.assets


    //默认英文字体
    fun getDefaultTypeFace(): Typeface {
        return Typeface.createFromAsset(mgr, "fonts/Cathena.ttf")
    }

    //柳公权字体
    fun getLiuGQTypeFace(): Typeface {
        return Typeface.createFromAsset(mgr, "fonts/FZLiuGQKSJW.ttf")
    }

    /**
     * 改变 TextView 字体
     * @param text TextView控件ID
     */
    fun load(typeface: Typeface, vararg text: TextView) {
        text.forEach {
            it.typeface = typeface
        }
    }

    /**
     * 改变 Button 字体
     * @param button Button控件ID
     */
    fun load(typeface: Typeface,vararg button: Button) {
        button.forEach {
            it.typeface = typeface
        }
    }

}