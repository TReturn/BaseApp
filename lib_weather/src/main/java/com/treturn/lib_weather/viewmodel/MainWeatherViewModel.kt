package com.treturn.lib_weather.viewmodel

import androidx.lifecycle.MutableLiveData
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate: 2023/6/15 15:23
 * @Author: 青柠
 * @Description:
 */
class MainWeatherViewModel:BaseViewModel() {

    val address = MutableLiveData<String>()

    val isShowCityManger = MutableLiveData<Boolean>()

    val isShowMagicIndicator = MutableLiveData<Boolean>()

    val isShowBackWeather = MutableLiveData<Boolean>()

    val isShowLocationImage = MutableLiveData<Boolean>()

    //显示广告
    var isShowAd = MutableLiveData<Boolean>()

    //是否显示刷新UI
    val isShowRefreshLayout = MutableLiveData<Boolean>()

    val refreshWeatherTime = MutableLiveData<String>()

    //骨架屏
    val isShowIncludeBg = MutableLiveData<Boolean>()

    //展示天气动效
    val isShowMainWeatherLottie = MutableLiveData<Boolean>()

    //展示天气背景
    val isShowMainWeatherBG = MutableLiveData<Boolean>()

    //是否展示天气详情ViewPager，避免与骨架屏滑动冲突
    var isShowWeatherViewPager = MutableLiveData<Boolean>()

    //是否显示开启定位服务UI
    val isShowLocationLayout = MutableLiveData<Boolean>()

}