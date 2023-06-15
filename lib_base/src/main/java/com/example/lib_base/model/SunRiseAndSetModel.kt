package com.example.lib_base.model

/**
 * @CreateDate: 2022/8/4 09:47
 * @Author: 青柠
 * @Description:
 */
data class SunRiseAndSetModel(
    //日出小时
    var sunRiseHour: Int = 5,
    //日出分钟
    var sunRiseMinute: Int = 0,
    //日落小时
    var sunSetHour: Int = 19,
    //日落分钟
    var sunSetMinute: Int = 0,
    //天气
    var weatherText: String = "",
)