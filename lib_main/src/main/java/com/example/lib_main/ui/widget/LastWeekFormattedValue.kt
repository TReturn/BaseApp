package com.example.lib_main.ui.widget

import com.example.lib_base.utils.time.GetLastWeekDateUtils
import com.github.mikephil.charting.formatter.ValueFormatter

/**
 * @CreateDate: 2022/7/5 10:06
 * @Author: 青柠
 * @Description:
 */
class LastWeekFormattedValue : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        //返回过去七天的日期数组
        val weekList = GetLastWeekDateUtils.getDays(7)
        return weekList[value.toInt()]
    }
}


