package com.example.lib_base.constant

/**
 * @CreateDate: 2020/12/30
 * @Author: 青柠
 * @Description: 存放网络请求URL
 */
object ApiUrls {

    const val BASE_WAN_URL = "https://www.wanandroid.com/"

    //百度搜索
    const val BAIDU_SEARCH_URL = "https://www.baidu.com/s?ie=UTF-8&wd="

    //百度IP地址定位
    const val BAIDU_IP_LOCATION_URL = "https://api.map.baidu.com/location/ip"

    //必应每日一图
    const val BING_PICTURE = "http://guolin.tech/api/bing_pic"

    //古诗Token,只需获取一次
    const val ANCIENT_CHINESE_POETRY_TOKEN = "https://v2.jinrishici.com/token"

    //古诗
    const val ANCIENT_CHINESE_POETRY = "https://v2.jinrishici.com/sentence"


    /**
     * 彩云天气相关API
     */

    //彩云天气API
    const val CAIYUN_WEATHER_REALTIME =
        "https://api.caiyunapp.com/v2.5/${SdkKeys.CAIYUN_WEATHER_KEY}"

    //实况天气
    fun getRealTimeWeather(lng: String, lat: String): String {
        return "${CAIYUN_WEATHER_REALTIME}/${lng},${lat}/realtime.json"
    }


    /**
     * 干货集中营相关API
     */

    //轮播
    const val GANK_CAROUSEL = "/banners"

    /**
     * 首页文章列表
     * @param pageNo Int
     * @return String
     */
    fun getMainArticle(pageNo: Int = 0): String {
        return "article/list/$pageNo/json"
    }

    //文章所有子分类
    const val WAN_CATEGORY = "project/tree/json"

    /**
     * 具体子分类下的列表
     * @param type 类型：“Android”
     * @param pageNo Int
     * @param pageSize Int
     * @return String
     */
    fun getWanCategoryType(pageNo: Int = 1): String {
        return "project/list/$pageNo/json"
    }
}