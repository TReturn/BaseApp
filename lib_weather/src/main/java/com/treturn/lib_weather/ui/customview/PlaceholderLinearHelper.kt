package com.treturn.lib_weather.ui.customview

import android.graphics.Color
import android.view.View
import android.view.animation.LinearInterpolator
import me.samlss.broccoli.Broccoli
import me.samlss.broccoli.BroccoliGradientDrawable
import me.samlss.broccoli.PlaceholderParameter

/**
 * @CreateDate: 2022/6/16 17:35
 * @Author: 青柠
 * @Description: 骨架屏阴影动效
 */
class PlaceholderLinearHelper private constructor() {
    companion object {
        fun getParameter(broccoli: Broccoli, vararg views: View): PlaceholderParameter? {
            broccoli.removeAllPlaceholders()
            for (i in views) {
                broccoli.addPlaceholder(
                    PlaceholderParameter.Builder()
                        .setView(i)
                        .setDrawable(
                            BroccoliGradientDrawable(
                                Color.parseColor("#F8F8F8"),
                                Color.parseColor("#F0F0F0"), 16F, 1000, LinearInterpolator()
                            )
                        )
                        .build()
                )
            }
            return null
        }
    }

    init {
        throw UnsupportedOperationException("Can not be instantiated.")
    }
}