package com.example.lib_main.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.scopeNetLife
import com.drake.net.Get
import com.example.lib_base.constant.ApiUrls
import com.example.lib_base.constant.SdkKeys
import com.example.lib_base.list.ListDataUiState
import com.example.lib_base.net.JvHeSerializationConverter
import com.example.lib_main.model.NewsDataBean
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate : 2021/8/8 1:04
 * @Author : 青柠
 * @Description :
 */
class NewsViewModel : BaseViewModel() {

    private var pageNo = 1
    private var isFirstEmpty = false

    //新闻列表集合数据
    var newsDataState = MutableLiveData<ListDataUiState<NewsDataBean.Result.Data>>()

    /**
     * 获取新闻列表
     * @param isRefresh 是否刷新
     * @param newsType 新闻类型
     */
    fun getNewsData(isRefresh: Boolean, newsType: String, pageSize: Int = 30) {
        if (isRefresh) {
            pageNo = 1
        }

        scopeNetLife {
            val data = Get<NewsDataBean>(ApiUrls.JVHE_NEWS) {
                converter = JvHeSerializationConverter()
                param("page", pageNo)
                param("page_size", pageSize)
                param("is_filter", "1")
                param("type", newsType)
                param("key", SdkKeys.JVHE_NEWS_KEY)
            }.await().result

            //请求成功
            isFirstEmpty = data.data.isEmpty() && pageNo == 1
            for (i in data.data.indices) {
                if (!TextUtils.isEmpty(data.data[i].thumbnailPicS03)) {
                    data.data[i].itemType = 2
                }
            }

            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isFirstEmpty = isFirstEmpty,
                    listData = data.data
                )

            pageNo++
            newsDataState.value = listDataUiState
        }.catch {
            // 协程内部发生错误回调, it为异常
            handleError(it)
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    isRefresh = isRefresh,
                    isFirstEmpty = false,
                    listData = arrayListOf<NewsDataBean.Result.Data>()
                )
            newsDataState.value = listDataUiState
        }
    }

}