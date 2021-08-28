package com.example.lib_main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.scopeNetLife
import com.drake.net.Get
import com.example.lib_base.constant.ApiUrls
import com.example.lib_base.constant.SdkKeys
import com.example.lib_base.list.ListDataUiState
import com.example.lib_base.net.GankSerializationConverter
import com.example.lib_base.net.JvHeSerializationConverter
import com.example.lib_base.net.SerializationConverter
import com.example.lib_base.net.WeatherSerializationConverter
import com.example.lib_base.utils.banner.BaseBannerData
import com.example.lib_base.utils.ui.ResourceUtils
import com.example.lib_main.R
import com.example.lib_main.model.*
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import java.util.ArrayList

/**
 * @CreateDate : 2021/7/29
 * @Author : 青柠
 * @Description :
 */
class MainViewModel : BaseViewModel() {

    private var pageNo = 1
    private var isFirstEmpty = false

    //妹子列表集合数据
    var beautyDataState = MutableLiveData<ListDataUiState<BeautyBean.Data>>()

    //轮播图数据
    val bannerDataState = MutableLiveData<List<BaseBannerData>>()

    //天气集合数据
    val weatherDataState = MutableLiveData<WeatherBean.Result>()

    //天气信息数据
    val weatherTemp = MutableLiveData<String>()
    val weatherSky = MutableLiveData<String>()

    val isShowWeather = MutableLiveData<Boolean>()

    //是否显示右下角可移动贴边View
    val isShowFruitView= MutableLiveData<Boolean>()

    /**
     * 获取妹子列表
     * @param isRefresh 是否刷新
     */
    fun getBeautyData(isRefresh: Boolean,pageSize:Int = 10) {
        if (isRefresh) {
            pageNo = 1
        }

        scopeNetLife {
            val data = Get<BeautyBean>(ApiUrls.getGankBeauty(pageNo,pageSize)) {
            }.await().data

            //请求成功
            isFirstEmpty = data.isEmpty() && pageNo == 1
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isFirstEmpty = isFirstEmpty,
                    listData = data
                )

            pageNo++
            beautyDataState.value = listDataUiState
        }.catch {
            // 协程内部发生错误回调, it为异常
            handleError(it)
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    isRefresh = isRefresh,
                    isFirstEmpty = false,
                    listData = arrayListOf<BeautyBean.Data>()
                )
            beautyDataState.value = listDataUiState
        }
    }


    /**
     * 获取Banner列表
     */
    fun getBannerData() {
        scopeNetLife {
            val data = Get<GankBannerBean>(ApiUrls.GANK_CAROUSEL) {
            }.await().data

            val outData: MutableList<BaseBannerData> = ArrayList<BaseBannerData>()
            for (i in data.indices) {
                val inData = BaseBannerData()
                inData.setImage(data[i].image)
                inData.setUrl(data[i].url)
                inData.setTitle(data[i].title)
                outData.add(inData)
            }
            bannerDataState.value = outData
        }
    }

    /**
     * 获取天气
     */
    fun getWeather(lng: String = "121.6544", lat: String = "25.1552") {
        scopeNetLife {
            val data = Get<WeatherBean>(ApiUrls.getRealTimeWeather(lng, lat)) {
                converter = WeatherSerializationConverter()
            }.await().result

            weatherDataState.value = data
        }
    }

    /**
     * 百度API获取IP定位
     */
    fun getIPLocation() {
        scopeNetLife {
            val data = Get<IPLocationBean>(ApiUrls.BAIDU_IP_LOCATION_URL) {
                converter = SerializationConverter()
                param("ak", SdkKeys.BAIDU_IP_LOCATION_KEY)
                param("coor", "gcj02")
            }.await().content

            getWeather(data.point.x, data.point.y)
        }
    }

    init {
        weatherSky.value = ResourceUtils.getString(R.string.main_is_loading)
        isShowFruitView.value = false
    }

}