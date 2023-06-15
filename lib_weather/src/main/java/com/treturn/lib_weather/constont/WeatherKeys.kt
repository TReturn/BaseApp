package com.treturn.lib_weather.constont

/**
 * @CreateDate: 2020/1/4
 * @Author: 青柠
 * @Description: 《阳光天气》静态常量，存储MMKV KEY值
 */
object WeatherKeys {

    // 是否添加城市了
    const val IS_USER_ADD_CITY = "IS_USER_ADD_CITY"

    //壁纸有声
    const val IS_WALLPAPER_AUDIO = "IS_WALLPAPER_AUDIO"

    //未看广告存4，看完广告存39
    const val UN_LOCK_40_DAY_WEATHER = "UN_LOCK_40_DAY_WEATHER"

    //天气通知栏开启
    const val IS_WEATHER_NOTIFICATION = "IS_WEATHER_NOTIFICATION"

    //天气预警开启
    const val IS_WEATHER_ALARM = "IS_WEATHER_ALARM"

    //早间提醒开启
    const val IS_DAY_WEATHER_REMIND = "IS_DAY_WEATHER_REMIND"
    const val SP_DAY_WEATHER_REMIND_TIME = "SP_DAY_WEATHER_REMIND_TIME"

    //晚间提醒开启
    const val IS_NIGHT_WEATHER_REMIND = "IS_NIGHT_WEATHER_REMIND"
    const val SP_NIGHT_WEATHER_REMIND_TIME = "SP_NIGHT_WEATHER_REMIND_TIME"


    //解锁天气信息
    const val SP_UNLOCK_DAYS_KEY = "sp_weather_data_unlock"

    //解锁天气信息时间
    const val SP_UNLOCK_DAYS_TIME = "sp_weather_unlock_time"

    //解锁天气趋势
    const val SP_UNLOCK_TREND_KEY = "sp_weather_trend_unlock"

    //解锁天气趋势时间
    const val SP_UNLOCK_TREND_TIME = "sp_weather_trend_time"

    // 当前定位城市
    const val SP_SAVE_LOCATION_CITY = "sp_save_location_city"

    //首页5个引导
    const val KEY_WEATHER_SET_MAIN_GUIDE = "KEY_WEATHER_SET_MAIN_GUIDE"

    //是否已经开始引导（避免重复出现引导）
    const val KEY_WEATHER_MAIN_GUIDE_SHOW_NOW = "KEY_WEATHER_MAIN_GUIDE_SHOW_NOW"

    //首页定位成功时间
    const val LOCATION_SUCCESS_TIME = "LOCATION_SUCCESS_TIME"

    const val WEATHER_DAYS_UN_LOCK_RATE = "WEATHER_DAYS_UN_LOCK_RATE"

    const val WEATHER_DAYS_UN_LOCK_TYPE = "WEATHER_DAYS_UN_LOCK_TYPE"

    const val WEATHER_TREND_UN_LOCK_TYPE = "WEATHER_TREND_UN_LOCK_TYPE"

    //首页插屏广告图片上次显示时间
    const val MAIN_WEATHER_AD_TIME = "MAIN_WEATHER_AD_TIME"

    //已添加的城市是否有定位城市
    const val IS_HAVE_LOCATION = "IS_HAVE_LOCATION"

    //首次安装进入首页不定位
    const val IS_FIRST_LOCATION = "IS_FIRST_LOCATION"

    //上次定位经度
    const val LAST_LOCATION_LONGITUDE = "LAST_LOCATION_LONGITUDE"

    //上次定位纬度
    const val LAST_LOCATION_LATITUDE = "LAST_LOCATION_LATITUDE"

    //解锁日历信息
    const val SP_UNLOCK_CALENDAR_KEY = "SP_UNLOCK_CALENDAR_KEY"

    //解锁日历时间
    const val SP_UNLOCK_CALENDAR_TIME = "SP_UNLOCK_CALENDAR_TIME"

    //首次进入主页
    const val IS_FIRST_TO_MAIN = "IS_FIRST_TO_MAIN"

    const val CALENDAR_UN_LOCK_RATE = "CALENDAR_UN_LOCK_RATE"

    const val CALENDAR_UN_LOCK_TYPE = "CALENDAR_UN_LOCK_TYPE"

    //30天天气广告按钮01
    const val WEATHER_DAYS_AD_BUTTON_01_TIME = "WEATHER_DAYS_AD_BUTTON_01_TIME"

    const val WEATHER_DAYS_AD_BUTTON_01_COUNT = "WEATHER_DAYS_AD_BUTTON_01_COUNT"

    //默认城市背景，为第一个城市的最后获取到的天气背景
    const val WEATHER_MAIN_FIRST_CITY_BG = "WEATHER_MAIN_FIRST_CITY_BG"

    //播放讯飞语音播报次数
    const val PLAY_CLOUD_VOICE_NUM = "PLAY_CLOUD_VOICE_NUM"

    //播放讯飞语音播报时间
    const val PLAY_CLOUD_VOICE_TIME = "PLAY_CLOUD_VOICE_TIME"

    //存储城市名
    const val NOW_SELECT_CITY_NAME = "NOW_SELECT_CITY_NAME"

    //存储经纬度
    const val NOW_SELECT_CITY_LONLAT = "NOW_SELECT_CITY_LONLAT"

    //存储城市CODE
    const val NOW_SELECT_CITY_AREA_CODE = "NOW_SELECT_CITY_AREA_CODE"

    //存储是否为定位城市
    const val NOW_SELECT_CITY_IS_LOCATION = "NOW_SELECT_CITY_IS_LOCATION"

    //首页天气城市size
    const val NEW_FRAGMENT_LIST_SIZE = "NEW_FRAGMENT_LIST_SIZE"

    //是否删除、添加定位城市
    const val IS_DELETE_LOCATION_CITY = "IS_DELETE_LOCATION_CITY"

    //未添加城市，隐藏添加城市按钮，屏蔽返回键
    const val IS_HIDE_CITY_ADD_FRAGMENT_BACK = "IS_HIDE_CITY_ADD_FRAGMENT_BACK"
}
