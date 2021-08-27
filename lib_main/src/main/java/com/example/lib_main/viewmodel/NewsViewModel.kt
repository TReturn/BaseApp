package com.example.lib_main.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.scopeNetLife
import com.drake.net.Get
import com.example.lib_base.constant.ApiUrls
import com.example.lib_base.constant.SdkKeys
import com.example.lib_base.list.ListDataUiState
import com.example.lib_base.net.JvHeSerializationConverter
import com.example.lib_base.utils.calculate.RegexUtils
import com.example.lib_base.utils.data.RandomUtils
import com.example.lib_main.model.BeautyBean
import com.example.lib_main.model.GankCategoryBean
import com.example.lib_main.model.GankCategoryTypeBean
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


    //具体子分类下的列表
    var categoryTypeDataState = MutableLiveData<ListDataUiState<GankCategoryTypeBean.Data>>()


    /**
     * 获取具体子分类下的列表
     * @param isRefresh 是否刷新
     * @param type 类型
     */
    fun getCategoryTypeData(isRefresh: Boolean, type: String, pageSize: Int = 10) {
        if (isRefresh) {
            pageNo = 1
        }

        scopeNetLife {
            val data =
                Get<GankCategoryTypeBean>(ApiUrls.getGankCategoryType(type, pageNo, pageSize)) {
                }.await().data

            //请求成功
            isFirstEmpty = data.isEmpty() && pageNo == 1
            for (i in data.indices) {
                //移除掉不正确的图片URL地址
                for (j in data[i].images.indices) {
                    if (!RegexUtils.isUrl(data[i].images[j])) {
                        data[i].images.removeAt(j)
                    }
                }
                //多布局：0：无图片，1：一张图，2：三张图
                data[i].itemType = when {
                    data[i].images.size == 2 -> {
                        1
                    }
                    data[i].images.size >= 3 -> {
                        2
                    }
                    else -> {
                        data[i].images.size
                    }
                }
            }

            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isFirstEmpty = isFirstEmpty,
                    listData = data
                )

            pageNo++
            categoryTypeDataState.value = listDataUiState
        }.catch {
            // 协程内部发生错误回调, it为异常
            handleError(it)
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    isRefresh = isRefresh,
                    isFirstEmpty = false,
                    listData = arrayListOf<GankCategoryTypeBean.Data>()
                )
            categoryTypeDataState.value = listDataUiState
        }
    }

}