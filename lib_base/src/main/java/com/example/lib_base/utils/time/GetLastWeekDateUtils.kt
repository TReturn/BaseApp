package com.example.lib_base.utils.time

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * @CreateDate: 2022/7/1 15:16
 * @Author: 青柠
 * @Description: 获取过去一周日期
 */
object GetLastWeekDateUtils {

    fun getWeek(dateTime: String): String {
        var week = ""
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        try {
            val date: Date = sdf.parse(dateTime)
            val dateFm = SimpleDateFormat("EEEE")
            week = dateFm.format(date)
            week = week.replace("星期".toRegex(), "周")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return week
    }

    /**
     * 获取过去7天内的日期数组
     * @param intervals      intervals天内
     * @return              日期数组
     */
    fun getDays(intervals: Int): ArrayList<String> {
        val pastDaysList: ArrayList<String> = ArrayList()
        for (i in intervals - 1 downTo 0) {
            pastDaysList.add(getPastDate(i))
        }
        return pastDaysList
    }

    /**
     * 获取过去第几天的日期
     * @param past
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    private fun getPastDate(past: Int): String {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past)
        val today: Date = calendar.time
        val format = SimpleDateFormat("MM/dd")
        return format.format(today)
    }
}