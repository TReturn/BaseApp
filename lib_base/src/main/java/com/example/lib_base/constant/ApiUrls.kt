package com.example.lib_base.constant

/**
 * @CreateDate: 2020/12/30
 * @Author: 青柠
 * @Description: 存放网络请求URL
 */
object ApiUrls {

    const val BASE_GANK_URL = "https://gank.io/api/v2"

    //百度搜索
    const val BAIDU_SEARCH_URL = "https://www.baidu.com/s?ie=UTF-8&wd="

    //百度搜索
    const val BAIDU_IP_LOCATION_URL = "https://api.map.baidu.com/location/ip"

    //聚合头条新闻
    const val JVHE_NEWS = "http://v.juhe.cn/toutiao/index"

    //聚合图书电商数据
    const val JVHE_BOOKS = "http://apis.juhe.cn/goodbook/query"

    //干货集中营轮播API
    const val GANK_CAROUSEL = "/banners"


    //彩云天气API
    const val CAIYUN_WEATHER_REALTIME =
        "https://api.caiyunapp.com/v2.5/${SdkKeys.CAIYUN_WEATHER_KEY}"

    //实况天气
    fun getRealTimeWeather(lng: String, lat: String): String {
        return "${CAIYUN_WEATHER_REALTIME}/${lng},${lat}/realtime.json"
    }

    //干货集中营妹子API
    fun getGankBeauty(pageNo:Int = 1,pageSize:Int = 10):String{
        return "/data/category/Girl/type/Girl/page/$pageNo/count/$pageSize"
    }

}