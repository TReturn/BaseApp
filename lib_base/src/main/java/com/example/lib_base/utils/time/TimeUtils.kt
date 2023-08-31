package com.example.lib_base.utils.time

import android.os.SystemClock
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * 时间工具
 */
object TimeUtils {

    private var lastServerTime: Long = System.currentTimeMillis() / 1000
    private var lastBootTime: Long = SystemClock.elapsedRealtime()

    private val SDF_THREAD_LOCAL = ThreadLocal<SimpleDateFormat>()

    val dateFormatYMDHMS ="yyyy-MM-dd-HH-mm-ss-SSS"

    val dateFormatYMD = "yyyy-MM-dd"

    val datePointFormatHH = "HH"
    val datePointFormatMM = "mm"

    //服务器(网络)时间
    @JvmStatic
    val serverTime: Long
        get() = (SystemClock.elapsedRealtime() - lastBootTime) / 1000 + lastServerTime

    private val calendar = Calendar.getInstance()

    //匹配“YYYY-MM-dd”格式
    private const val DATE_YEAR_MONTH_DAY_REGEX = "^(\\d{4})-([0-1]\\d)-([0-3]\\d)$"

    //匹配“YYYY-MM-dd HH:mm”格式
    private const val DATE_Y_M_D_H_M_REGEX =
        "^(\\d{4})-([0-1]\\d)-([0-3]\\d)\\s([0-5]\\d):([0-5]\\d)$"

    //匹配“YYYY-MM-dd HH:mm:ss”格式
    private const val DATE_Y_M_D_H_M_S_REGEX =
        "^(\\d{4})-([0-1]\\d)-([0-3]\\d)\\s([0-5]\\d):([0-5]\\d):([0-5]\\d)$"

    /**
     *  获取当天0点的时间戳
     */
    @JvmStatic
    fun getTodayTimeStamp(): Long {
        return Calendar.getInstance().apply {
            set(get(Calendar.YEAR), get(Calendar.MONTH), get(Calendar.DAY_OF_MONTH), 0, 0, 0)
        }.timeInMillis
    }

    /**
     *  将“YYYY-MM-dd”转成“MM/dd”格式
     */
    @JvmStatic
    fun formatDateToMMDD(date: String): String {
        if (!Pattern.matches(DATE_YEAR_MONTH_DAY_REGEX, date))
            return date

        val parts = date.split("-")
        return "${parts[1]}/${parts[2]}"
    }

    /**
     *  获取“YYYY-MM-dd”的周几
     */
    @JvmStatic
    fun getWeekYYYYMMdd(date: String): String {
        if (!Pattern.matches(DATE_YEAR_MONTH_DAY_REGEX, date))
            return "今天"
        val parts = date.split("-")

        calendar.set(parts[0].toInt(), parts[1].toInt() - 1, parts[2].toInt())

        val mCalendar = Calendar.getInstance()
        if (calendar.get(Calendar.YEAR) == mCalendar.get(Calendar.YEAR) &&
            calendar.get(Calendar.MONTH) == mCalendar.get(Calendar.MONTH) &&
            calendar.get(Calendar.DAY_OF_MONTH) == mCalendar.get(Calendar.DAY_OF_MONTH)
        ) {
            return "今天"
        }

        return when (calendar.get(Calendar.DAY_OF_WEEK) - 1) {
            1 -> "周一"
            2 -> "周二"
            3 -> "周三"
            4 -> "周四"
            5 -> "周五"
            6 -> "周六"
            0 -> "周日"
            else -> "今天"
        }
    }

    /**
     *  从“YYYY-MM-dd HH:mm”抽出时分
     */
    @JvmStatic
    fun getHourMinute(date: String): String {
        if (!Pattern.matches(DATE_Y_M_D_H_M_REGEX, date))
            return date
        val parts = date.split(" ")
        return parts[1]
    }

    /**
     *  从“YYYY-MM-dd HH:mm:ss”抽出时分
     */
    @JvmStatic
    fun getHourMinuteForDate(date: String): String {
        if (!Pattern.matches(DATE_Y_M_D_H_M_S_REGEX, date))
            return date
        val parts = date.split(" ")
        val parts1 = parts[1].split(":")
        return "${parts1[0]}:${parts1[1]}"
    }

    /**
     *  判断该日期是否是当天
     */
    @JvmStatic
    fun isToday(date: String): Boolean {
        if (!Pattern.matches(DATE_YEAR_MONTH_DAY_REGEX, date))
            return false
        val parts = date.split("-")
        calendar.set(parts[0].toInt(), parts[1].toInt() - 1, parts[2].toInt())

        val mCalendar = Calendar.getInstance()

        return (calendar.get(Calendar.YEAR) == mCalendar.get(Calendar.YEAR) &&
                calendar.get(Calendar.MONTH) == mCalendar.get(Calendar.MONTH) &&
                calendar.get(Calendar.DAY_OF_MONTH) == mCalendar.get(Calendar.DAY_OF_MONTH))
    }

    /**
     *  获取现在"HH:mm"的时间格式
     */
    @JvmStatic
    fun getTimeForNowHHmm(): String {
        val calendar = Calendar.getInstance()
        //格式化时
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val formatHour = if (hour <= 9) "0$hour" else "$hour"

        //格式化分
        val minute = calendar.get(Calendar.MINUTE)
        val formatMinute = if (minute <= 9) "0$minute" else "$minute"
        return "${formatHour}:${formatMinute}"
    }

    /**
     *  获取现在"MM/dd"的时间格式
     */
    @JvmStatic
    fun getTimeForNowMMdd(): String {
        val calendar = Calendar.getInstance()
        //格式化月份
        val month = calendar.get(Calendar.MONTH) + 1
        val formatMonth = if (month <= 9) "0$month" else "$month"

        //格式化日
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val formatDay = if (day <= 9) "0$day" else "$day"
        return "${formatMonth}/${formatDay}"
    }

    /**
     * 时间差
     *
     * @param date
     * @return
     */
    @JvmStatic
    fun getTimeFormatText(timeStr: String): String {
        val minute = (60 * 1000).toLong() // 1分钟
        val hour = 60 * minute // 1小时
        val day = 24 * hour // 1天
        val month = 31 * day // 月
        val year = 12 * month // 年

        val diff = Date().time - getStringByFormat(timeStr, "yyyy-MM-dd HH:mm:ss").time
        var r: Long = 0
        if (diff > year) {
            r = diff / year
            return r.toString() + "年前"
        }
        if (diff > month) {
            r = diff / month
            return r.toString() + "个月前"
        }
        if (diff > day) {
            r = diff / day
            return r.toString() + "天前"
        }
        if (diff > hour) {
            r = diff / hour
            return r.toString() + "小时前"
        }
        if (diff > minute) {
            r = diff / minute
            return r.toString() + "分钟前"
        }
        return "刚刚"
    }

    private fun getDateFormat(pattern: String): SimpleDateFormat {
        var simpleDateFormat: SimpleDateFormat = SDF_THREAD_LOCAL.get()
        if (simpleDateFormat == null) {
            simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
            SDF_THREAD_LOCAL.set(simpleDateFormat)
        } else {
            simpleDateFormat.applyPattern(pattern)
        }
        return simpleDateFormat
    }

    /**
     * 描述：根据时间转换为--时间日期格式化到年月日时分秒毫秒
     *
     * @param milliseconds the milliseconds
     * @return String 日期时间字符串
     */
    fun getStringByFormat(milliseconds: Long, format: String): String {
        return getDateFormat(format).format(milliseconds)
    }

    fun getStringByFormat(date: Date?, format: String): String {
        return getDateFormat(format).format(date)
    }

    fun getStringByFormat(date: String, format: String): Date {
        return try {
            getDateFormat(format).parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            Date()
        }
    }

    /**
     * 是否是同一天
     *
     * @param milliseconds
     * @return
     */
    fun isSameDate(milliseconds: Long): Boolean {
        return (getStringByFormat(
            milliseconds,
            dateFormatYMD
        ) == getStringByFormat(System.currentTimeMillis(), dateFormatYMD))
    }

}