package com.example.lib_base.utils.ui

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.SpannableString
import com.example.lib_base.BaseApplication

/**
 * @CreateDate : 2021/8/25
 * @Author : 青柠
 * @Description : 获取本地文本、颜色、图片资源
 */
object UiUtils {

    //获取文本
    fun getString(resId: Int): String {
        return BaseApplication.context.resources.getString(resId)
    }

    fun getString(stringID: Int, args: Any?): String {
        return BaseApplication.context.getString(stringID, args)
    }

    fun getString(stringID: Int, vararg args: Any?): String {
        return BaseApplication.context.getString(stringID, args)
    }

    fun getStringArray(resId: Int): Array<out String> {
        return BaseApplication.context.resources.getStringArray(resId)
    }

    fun getIntArray(resId: Int): IntArray {
        return BaseApplication.context.resources.getIntArray(resId)
    }

    //获取颜色
    fun getColor(colorId: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            BaseApplication.context.resources.getColor(colorId, BaseApplication.context.theme)
        } else {
            BaseApplication.context.resources.getColor(colorId)
        }
    }

    //获取图片
    @SuppressLint("UseCompatLoadingForDrawables")
    fun getDrawable(drawableId: Int): Drawable {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BaseApplication.context.resources.getDrawable(drawableId, BaseApplication.context.theme)
        } else {
            BaseApplication.context.resources.getDrawable(drawableId)
        }
    }

    fun setSpan(
        ss: SpannableString,
        what: Any,
        source: String,
        target: String,
        flag: Int
    ): SpannableString {
        val index = source.indexOf(target)
        if (index != -1) {
            ss.setSpan(what, index, index + target.length, flag)
        }
        return ss
    }


}