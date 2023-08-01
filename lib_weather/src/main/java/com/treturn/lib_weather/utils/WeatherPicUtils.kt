package com.treturn.lib_weather.utils

import com.example.lib_base.model.SunRiseAndSetModel
import com.example.lib_base.utils.time.TimeUtils
import com.treturn.lib_weather.R
import com.treturn.lib_weather.model.WeatherPicModel


/**
 * @CreateDate: 2022/6/20 11:03
 * @Author: 青柠
 * @Description: 天气对应的背景图和动效
 */
object WeatherPicUtils {
    /**
     *
     * @param model SunRiseAndSetModel 天气名称，日出日落时间
     * @return WeatherPicModel 背景图和动效
     */
    fun get(model: SunRiseAndSetModel): WeatherPicModel {
        var picResId = R.drawable.pic_main_weather_qingtian
        var imageAssetsFolder = ""
        var imageAssetsJson = ""

        //当前小时
        val hour = TimeUtils.getStringByFormat(
            System.currentTimeMillis(),
            TimeUtils.datePointFormatHH
        ).toInt()

        //当前分
        val minute = TimeUtils.getStringByFormat(
            System.currentTimeMillis(),
            TimeUtils.datePointFormatMM
        ).toInt()

        when (model.weatherText) {
            "多云", "夜间有云" -> {
                picResId = R.drawable.pic_main_weather_duoyun
                imageAssetsFolder = "main_weather_duoyun"
                imageAssetsJson = "main_weather_duoyun.json"
                if (hour !in model.sunRiseHour until model.sunSetHour) {
                    if (model.sunSetHour == hour && minute >= model.sunSetMinute || hour > model.sunSetHour) {
                        picResId = R.drawable.pic_main_weather_duoyun_night
                        imageAssetsFolder = "main_weather_duoyun_night"
                        imageAssetsJson = "main_weather_duoyun_night.json"
                    }
                }
            }
            "小雨", "中雨", "大雨", "阵雨", "雨夹雪", "多云转小雨", "暴雨", "大暴雨", "特大暴雨", "冻雨", "小到中雨", "中到大雨", "大到暴雨", "暴雨到大暴雨", "大暴雨到特大暴雨" -> {
                picResId = R.drawable.pic_main_weather_xiayu
                if (hour !in model.sunRiseHour until model.sunSetHour) {
                    if (model.sunSetHour == hour && minute >= model.sunSetMinute || hour > model.sunSetHour) {
                        picResId = R.drawable.pic_main_weather_duoyun_night
                    }
                }
                when (model.weatherText) {
                    "小雨", "阵雨", "雨夹雪", "小到中雨" -> {
                        imageAssetsFolder = "main_weather_xiaoyu"
                        imageAssetsJson = "main_weather_xiaoyu.json"
                    }
                    "中雨", "中到大雨" -> {
                        imageAssetsFolder = "main_weather_zhongyu"
                        imageAssetsJson = "main_weather_zhongyu.json"
                    }
                    "大雨", "暴雨", "大暴雨", "特大暴雨", "冻雨", "大到暴雨", "暴雨到大暴雨", "大暴雨到特大暴雨" -> {
                        imageAssetsFolder = "main_weather_dayu"
                        imageAssetsJson = "main_weather_dayu.json"
                    }
                }
            }
            "雷阵雨", "雷阵雨伴有冰雹", "沙尘暴", "浮尘", "扬沙", "强沙尘暴", "霾", "中度霾", "重度霾", "严重霾", "晴天有霾", "夜间有霾" -> {
                picResId = R.drawable.pic_main_weather_leizhenyu
                if (hour !in model.sunRiseHour until model.sunSetHour) {
                    if (model.sunSetHour == hour && minute >= model.sunSetMinute || hour > model.sunSetHour) {
                        picResId = R.drawable.pic_main_weather_duoyun_night
                    }
                }
                if (model.weatherText == "雷阵雨" || model.weatherText == "雷阵雨伴有冰雹") {
                    imageAssetsFolder = "main_weather_leiyu"
                    imageAssetsJson = "main_weather_leiyu.json"
                }
            }
            "晴", "夜间晴", "多云转晴" -> {
                picResId = R.drawable.pic_main_weather_qingtian
                imageAssetsFolder = "main_weather_qingtian"
                imageAssetsJson = "main_weather_qingtian.json"
                if (hour !in model.sunRiseHour until model.sunSetHour) {
                    if (model.sunSetHour == hour && minute >= model.sunSetMinute || hour > model.sunSetHour) {
                        picResId = R.drawable.pic_main_weather_qingtian_night
                        imageAssetsFolder = "main_weather_qingtian_night"
                        imageAssetsJson = "main_weather_qingtian_night.json"
                    }
                }
            }
            "小雪", "中雪", "大雪", "多云转小雪", "阵雪", "暴雪", "小到中雪", "中到大雪", "大到暴雪", "弱高吹雪", "雪" -> {
                picResId = R.drawable.pic_main_weather_xiaxue
                imageAssetsFolder = "main_weather_xiaxue"
                imageAssetsJson = "main_weather_xiaxue.json"
            }
            "阴", "大风", "晴天有雾", "夜间有雾", "雾", "浓雾", "龙卷风", "轻雾", "强浓雾", "大雾", "特强浓雾" -> {
                picResId = R.drawable.pic_main_weather_yintian
                if (hour !in model.sunRiseHour until model.sunSetHour) {
                    if (model.sunSetHour == hour && minute >= model.sunSetMinute || hour > model.sunSetHour) {
                        picResId = R.drawable.pic_main_weather_duoyun_night
                    }
                }
                if (model.weatherText == "阴") {
                    imageAssetsFolder = "main_weather_yintian"
                    imageAssetsJson = "main_weather_yintian.json"
                }
            }
        }
        return WeatherPicModel(picResId, imageAssetsFolder, imageAssetsJson)
    }
}