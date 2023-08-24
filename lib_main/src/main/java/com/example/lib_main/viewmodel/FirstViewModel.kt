package com.example.lib_main.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.scopeNetLife
import com.drake.net.Get
import com.example.lib_base.constant.ApiUrls
import com.example.lib_base.constant.UserKeys
import com.example.lib_base.constant.SdkKeys
import com.example.lib_base.list.ListDataUiState
import com.example.lib_base.net.PoetrySerializationConverter
import com.example.lib_base.net.SerializationConverter
import com.example.lib_base.net.WeatherSerializationConverter
import com.example.lib_base.utils.data.MMKVUtils
import com.example.lib_base.utils.log.LogUtils
import com.example.lib_base.utils.ui.ResourceUtils
import com.example.lib_main.R
import com.example.lib_main.model.*
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate : 2021/8/27 14:28
 * @Author : 青柠
 * @Description :
 */
class FirstViewModel : BaseViewModel() {

    private var pageNo = 0
    private var isFirstEmpty = false

    //首页文章列表集合数据
    var articleDataState = MutableLiveData<ListDataUiState<ArticleDetail>>()

    //天气集合数据
    val weatherDataState = MutableLiveData<WeatherBean.Result>()

    //天气信息数据
    val weatherTemp = MutableLiveData<String>()
    val weatherSky = MutableLiveData<String>()

    val isShowWeather = MutableLiveData<Boolean>()

    //古诗返回结果
    val poetryResultDataState = MutableLiveData<PoetryBean.Data>()
    val poetryResult = MutableLiveData<String>()

    /**
     * 获取首页文章列表
     * @param isRefresh 是否刷新
     */
    fun getArticleData(isRefresh: Boolean, pageSize: Int = 10) {
        if (isRefresh) {
            pageNo = 0
        }

        scopeNetLife {
            val data = Get<ArticleModel>(ApiUrls.getMainArticle(pageNo)) {
                param("page_size ", pageSize.toString())
            }.await().data.datas

            //请求成功
            isFirstEmpty = data.isEmpty() && pageNo == 0
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isFirstEmpty = isFirstEmpty,
                    listData = data
                )

            pageNo++
            articleDataState.value = listDataUiState
        }.catch {
            // 协程内部发生错误回调, it为异常
            handleError(it)
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    isRefresh = isRefresh,
                    isFirstEmpty = false,
                    listData = arrayListOf<ArticleDetail>()
                )
            articleDataState.value = listDataUiState
        }
    }


    /**
     * 获取天气
     */
    private fun getWeather(lng: String = "121.6544", lat: String = "25.1552") {
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

    /**
     * 有TOKEN直接请求古诗内容，无TOKEN请求TOKEN
     */
    fun getPoetry() {
        if (TextUtils.isEmpty(MMKVUtils.getString(UserKeys.POETRY_TOKEN))) {
            getAncientChinesePoetryToken()
        } else {
            getAncientChinesePoetry()
        }
    }

    /**
     * 获取古诗Token
     */
    private fun getAncientChinesePoetryToken() {
        scopeNetLife {
            val data = Get<PoetryTokenBean>(ApiUrls.ANCIENT_CHINESE_POETRY_TOKEN) {
                converter = PoetrySerializationConverter()

            }.await().data

            MMKVUtils.put(UserKeys.POETRY_TOKEN, data)
            getAncientChinesePoetry()
        }
    }

    /**
     * 获取古诗
     */
    private fun getAncientChinesePoetry() {
        scopeNetLife {
            val data = Get<PoetryBean>(ApiUrls.ANCIENT_CHINESE_POETRY) {
                converter = PoetrySerializationConverter()
                setHeader("X-User-Token", MMKVUtils.getString(UserKeys.POETRY_TOKEN))
            }.await().data
            LogUtils.d(data)
            poetryResultDataState.value = data
        }
    }

    init {
        weatherSky.value = ResourceUtils.getString(R.string.main_is_loading)
    }
}