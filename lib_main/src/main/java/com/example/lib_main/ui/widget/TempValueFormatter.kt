package com.example.lib_main.ui.widget

import com.github.mikephil.charting.formatter.ValueFormatter

/**
 * @CreateDate: 2022/7/8 10:01
 * @Author: 青柠
 * @Description:
 */
class TempValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        //返回温度数据
        return "${value.toInt()}°"
    }
}