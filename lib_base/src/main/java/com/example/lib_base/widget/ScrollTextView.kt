package com.example.lib_base.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * @CreateDate: 2022/6/16 10:23
 * @Author: 青柠
 * @Description: 自动滚动的TextView,跑马灯效果
 */
class ScrollTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initView()
    }

    private fun initView() {
        ellipsize = TextUtils.TruncateAt.MARQUEE
        marqueeRepeatLimit = -1
        isSingleLine = true
    }

    constructor(context: Context, attrs: AttributeSet) : super(
        context, attrs
    ) {
        initView()
    }

    override fun isFocused(): Boolean {
        return true
    }
}