package com.example.lib_base.ext

import android.graphics.Paint
import android.text.InputFilter
import android.widget.EditText
import android.widget.TextView

/**
 * @CreateDate: 2023/2/21 16:02
 * @Author: 青柠
 * @Description:
 */

/**
 * 设置下划线
 * @receiver TextView
 */
fun TextView.underLine() {
    paint.apply {
        //下划线
        flags = Paint.UNDERLINE_TEXT_FLAG
        //抗锯齿
        isAntiAlias = true
    }
}

/**
 * 设置中间的线
 * @receiver TextView
 */
fun TextView.centerLine() {
    paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
}

/**
 * 设置最大输入长度
 * @receiver EditText
 * @param length Int
 */

fun TextView.maxLength(length: Int) {
    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(length))
}

fun EditText.maxLength(length: Int) {
    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(length))
}