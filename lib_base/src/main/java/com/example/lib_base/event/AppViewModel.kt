package com.example.lib_base.event

import com.example.lib_base.model.SunRiseAndSetModel
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.livedata.event.EventLiveData

/**
 * @CreateDate: 2022/6/14 11:35
 * @Author: 青柠
 * @Description: APP全局ViewModel,可在任何位置监听、回调，相当于EventBus。
 */
class AppViewModel : BaseViewModel() {

    //跳转到首页Tab，0第一个Tab，1第二个，以此类推
    val intentToMainTab = EventLiveData<Int>()

    //切换天气Tab改变相应的背景图和动效
    val changeMainWeatherPic = EventLiveData<SunRiseAndSetModel>()

    //当前首页统一显示选中的城市名称
    val currentUnifyWeatherCityName = EventLiveData<String>()

    //统一定位城市名称
    val currentUnifyLocationCityName = EventLiveData<String>()

    //当前定位城市名
    val currentLocationCityName = EventLiveData<String>()

    //当前首页选中的城市是否定位
    val currentIsLocationCityName = EventLiveData<Boolean>()

    val isShowFinishDialog = EventLiveData<Boolean>()

    // 跳转到目标城市天气
    val weatherSelectPosition = EventLiveData<Int>()

    //首页顶部折叠状态，折叠为true，展开为false
    val mainScrimsStatus = EventLiveData<Boolean>()

    //是否开启预加载（直接开启加载速度太慢）
    val isOffscreenPageLimit = EventLiveData<Boolean>()

    //首页折叠后的天气图标
    val mainWeatherText = EventLiveData<String>()

    //是否拦截资讯子View事件
    val isSlideNews = EventLiveData<Boolean>()

    //是否从资讯页返回天气
    val isBackWeather = EventLiveData<Boolean>()

    //已添加城市是否有变动 1.新增 2.新增定位城市 3.删除
    val cityListChangeType = EventLiveData<Int>()

    //讯飞语音播报,控制播放、停止、播放完成
    val playIFlyTekCloudVoice = EventLiveData<String>()
    val stopIFlyTekCloudVoice = EventLiveData<Boolean>()
    val finishIFlyTekCloudVoice = EventLiveData<Boolean>()

    //是否新增城市
    val isAddNewWeatherCity = EventLiveData<Boolean>()

    //关闭所有首页顶部广告
    val isCloseAllWeatherTopAd = EventLiveData<Boolean>()

    //天气几分钟前刷新
    var refreshWeatherText = EventLiveData<String>()

    //是否刷新天气
    val isRefreshWeatherData = EventLiveData<Boolean>()

    //是否结束刷新
    val isFinishRefreshWeatherData = EventLiveData<Boolean>()

    //是否开启刷新天气（折叠后关闭刷新，完全展开再开启，避免事件冲突）
    val isEnableRefreshWeather = EventLiveData<Boolean>()

    //天气数据是否加载完成
    val isWeatherConfigOver = EventLiveData<Boolean>()

    //显示返回天气按钮
    val isShowBackWeather = EventLiveData<Boolean>()

    //骨架屏
    val isShowIncludeBg = EventLiveData<Boolean>()

    //添加城市后关闭城市管理界面
    val isFinishCityManager = EventLiveData<Boolean>()

    //是否打开城市管理界面
    val isOpenCityManager = EventLiveData<Boolean>()
}