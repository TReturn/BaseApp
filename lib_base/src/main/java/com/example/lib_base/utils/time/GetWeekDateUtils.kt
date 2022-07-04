package com.example.lib_base.utils.time

import android.annotation.SuppressLint
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * @CreateDate: 2022/7/1 14:58
 * @Author: 青柠
 * @Description: 获取当前一周日期
 */
object GetWeekDateUtils {
    /**
     * 获取当前一周的一星期的日期
     * @return List<String>
     */
    fun getCurrentWeekAllDate(): List<String> {

        // 获取当天的毫秒时间
        val currentTimeMillis = System.currentTimeMillis()
        return getAllWeekDayDateByMillis(currentTimeMillis)
    }


    /**
     * 获取指定任意日期当周的一星期的日期
     * @param someDataStr String 传入日期，格式为：2019-10-15
     * @return List<String>
     */
    fun getSomedayWeekAllDate(someDataStr: String = "2019-10-15"): List<String> {

        // 指定天的毫秒时间
        val currentTimeMillis = getWhatDaySomeDateMillis(someDataStr)
        return getAllWeekDayDateByMillis(currentTimeMillis)
    }

    // someDataStr 格式："yyyy-MM-dd"
    // 返回 时间毫秒
    @SuppressLint("SimpleDateFormat")
    private fun getWhatDaySomeDateMillis(someDataStr: String): Long {
        var date = Date()
        val format = SimpleDateFormat("yyyy-MM-dd")
        try {
            date = format.parse(someDataStr)
            return date.time
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return -1
    }

    // 获取指定日期毫秒时间得到 星期几
    private fun getWhatDaySomeday(timeMillis: Long): String? {
        val toDayDate = Date(timeMillis)
        val formatE = SimpleDateFormat("E")
        var week: String? = null
        try {
            week = formatE.format(toDayDate)
            println(week)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return week
    }

    // 根据 星期几到获取与 星期一 相差几天
    private fun getHowManyDayFromMonday(someDay: String?): Int {
        var day = 0
        when (someDay) {
            "星期一" -> day = 0
            "星期二" -> day = 1
            "星期三" -> day = 2
            "星期四" -> day = 3
            "星期五" -> day = 4
            "星期六" -> day = 5
            "星期日" -> day = 6
            else -> println("不存在这样的星期几 : $someDay")
        }
        return day
    }

    // 获取指定日期毫秒时间的当周一星期的日期
    private fun getAllWeekDayDateByMillis(timeMills: Long): List<String> {
        val list: MutableList<String> = ArrayList()

        // 得到指定时间是周几
        val week = getWhatDaySomeday(timeMills)
        println("日期是：$week")

        // 记录与周一的间隔天数
        val dayFromMonday = getHowManyDayFromMonday(week)
        println("dayFromMonday : $dayFromMonday")

        // 获取这周第一天毫秒值
        val dayMillis = (24 * 60 * 60 * 1000).toLong()
        // 获取这周第一天的日子
        val firstOfWeekMillis = timeMills - dayFromMonday * dayMillis

        // 使用 for 循环得到当前一周的日子（7天的日子）
        var i = firstOfWeekMillis
        while (i < firstOfWeekMillis + 7 * dayMillis) {
            val targetDate = Date(i)
            @SuppressLint("SimpleDateFormat") val format = SimpleDateFormat("MM/dd")
            val targetDay = format.format(targetDate)
            list.add(targetDay)
            i += dayMillis
        }
        return list
    }
}