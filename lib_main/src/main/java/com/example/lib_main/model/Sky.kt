package com.example.lib_main.model


class Sky (val info: String, val lottie: String)

private val sky = mapOf(
    "CLEAR_DAY" to Sky("晴", "sunny_weather.json"),
    "CLEAR_NIGHT" to Sky("晴", "sunny_weather.json"),
//    "PARTLY_CLOUDY_DAY" to Sky("多云", "cloud_weather.json"),
//    "PARTLY_CLOUDY_NIGHT" to Sky("多云", "cloud_weather.json"),
//    "CLOUDY" to Sky("阴", "cloudy_weather.json.json"),
//    "WIND" to Sky("大风", "wind_weather.json"),
//    "LIGHT_RAIN" to Sky("小雨", "rain_weather.json"),
//    "MODERATE_RAIN" to Sky("中雨", "rain_weather.json"),
//    "HEAVY_RAIN" to Sky("大雨", "rain_weather.json"),
//    "STORM_RAIN" to Sky("暴雨", "rain_weather.json"),
//    "THUNDER_SHOWER" to Sky("雷阵雨","rain_weather.json"),
//    "SLEET" to Sky("雨夹雪", "snow_weather.json"),
//    "LIGHT_SNOW" to Sky("小雪", "snow_weather.json"),
//    "MODERATE_SNOW" to Sky("中雪", "snow_weather.json"),
//    "HEAVY_SNOW" to Sky("大雪", "snow_weather.json"),
//    "STORM_SNOW" to Sky("暴雪", "snow_weather.json"),
//    "HAIL" to Sky("冰雹", "snow_weather.json"),
//    "LIGHT_HAZE" to Sky("轻度雾霾", "fog_weather.json"),
//    "MODERATE_HAZE" to Sky("中度雾霾", "fog_weather.json"),
//    "HEAVY_HAZE" to Sky("重度雾霾", "fog_weather.json"),
//    "FOG" to Sky("雾", "fog_weather.json"),
//    "DUST" to Sky("浮尘", "fog_weather.json")
    )

fun getSky(skycon: String): Sky {
    return sky[skycon] ?: sky["CLEAR_DAY"]!!
}