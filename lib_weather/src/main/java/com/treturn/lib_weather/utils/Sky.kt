package com.treturn.lib_weather.utils

import com.treturn.lib_weather.R


/**
 * @CreateDate: 2022/4/21 18:58 下午
 * @Author: 青柠
 * @Description: 传入天气名称，返回天气图标
 * @property resId Int 图标资源ID
 */
class Sky(val resId: Int)

private val sky = mapOf(
    "晴" to Sky(R.drawable.ic_w_qing),
    "夜间晴" to Sky(R.drawable.ic_w_yjq),
    "多云转晴" to Sky(R.drawable.ic_w_dyzq),
    "多云" to Sky(R.drawable.ic_w_dyzq),
    "多云转小雨" to Sky(R.drawable.ic_w_dyzxy),
    "多云转小雪" to Sky(R.drawable.ic_w_dyzxx),
    "夜间有云" to Sky(R.drawable.ic_w_yjyy),
    "阴" to Sky(R.drawable.ic_w_yin),
    "大风" to Sky(R.drawable.ic_w_dafeng),
    "小雨" to Sky(R.drawable.ic_w_xiaoyu),
    "中雨" to Sky(R.drawable.ic_w_zhongyu),
    "大雨" to Sky(R.drawable.ic_w_dayu),
    "雷阵雨" to Sky(R.drawable.ic_w_lzy),
    "雨夹雪" to Sky(R.drawable.ic_w_yjx),
    "小雪" to Sky(R.drawable.ic_w_xiaoxue),
    "中雪" to Sky(R.drawable.ic_w_zhongxue),
    "大雪" to Sky(R.drawable.ic_w_daxue),
    "晴天有霾" to Sky(R.drawable.ic_w_qtym),
    "夜间有霾" to Sky(R.drawable.ic_w_yjym),
    "晴天有雾" to Sky(R.drawable.ic_w_qtyw),
    "夜间有雾" to Sky(R.drawable.ic_w_yjyw),

    "阵雨" to Sky(R.drawable.ic_w_zhenyu),
    "雷阵雨伴有冰雹" to Sky(R.drawable.ic_w_lzybybb),
    "暴雨" to Sky(R.drawable.ic_w_baoyu),
    "大暴雨" to Sky(R.drawable.ic_w_dby),
    "特大暴雨" to Sky(R.drawable.ic_w_tdby),
    "阵雪" to Sky(R.drawable.ic_w_zhenxue),
    "暴雪" to Sky(R.drawable.ic_w_baoyu),
    "雾" to Sky(R.drawable.ic_w_wu),
    "冻雨" to Sky(R.drawable.ic_w_dongyu),
    "沙尘暴" to Sky(R.drawable.ic_w_scb),
    "小到中雨" to Sky(R.drawable.ic_w_xdzy),
    "中到大雨" to Sky(R.drawable.ic_w_zddy),
    "大到暴雨" to Sky(R.drawable.ic_w_ddby),
    "暴雨到大暴雨" to Sky(R.drawable.ic_w_byddby),
    "大暴雨到特大暴雨" to Sky(R.drawable.ic_w_dbydtdby),

    "小到中雪" to Sky(R.drawable.ic_w_xdzx),
    "中到大雪" to Sky(R.drawable.ic_w_zddx),
    "大到暴雪" to Sky(R.drawable.ic_w_ddbx),
    "浮尘" to Sky(R.drawable.ic_w_fuchen),
    "扬沙" to Sky(R.drawable.ic_w_yangsha),
    "强沙尘暴" to Sky(R.drawable.ic_w_qscb),
    "浓雾" to Sky(R.drawable.ic_w_nongwu),
    "龙卷风" to Sky(R.drawable.ic_w_ljf),
    "弱高吹雪" to Sky(R.drawable.ic_w_rgcx),
    "轻雾" to Sky(R.drawable.ic_w_qingwu),
    "强浓雾" to Sky(R.drawable.ic_w_qnw),
    "霾" to Sky(R.drawable.ic_w_mai),
    "中度霾" to Sky(R.drawable.ic_w_zdm1),
    "重度霾" to Sky(R.drawable.ic_w_zdm2),
    "严重霾" to Sky(R.drawable.ic_w_yzm),
    "大雾" to Sky(R.drawable.ic_w_dawu),
    "特强浓雾" to Sky(R.drawable.ic_w_tqnw),
    "雨" to Sky(R.drawable.ic_w_yu),
    "雪" to Sky(R.drawable.ic_w_xue),
)

fun getSky(info: String): Sky {
    return sky[info] ?: sky["晴"]!!
}