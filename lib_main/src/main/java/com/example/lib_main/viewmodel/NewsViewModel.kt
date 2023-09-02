package com.example.lib_main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.scopeNetLife
import com.drake.net.Get
import com.example.lib_base.constant.ApiUrls
import com.example.lib_base.list.ListDataUiState
import com.example.lib_main.model.CategoryTypeData
import com.example.lib_main.model.WanCategoryTypeBean
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
    var categoryTypeDataState = MutableLiveData<ListDataUiState<CategoryTypeData>>()


    /**
     * 获取具体子分类下的列表
     * @param isRefresh 是否刷新
     * @param type 类型
     */
    fun getCategoryTypeData(isRefresh: Boolean, cid: String, pageSize: Int = 10) {
        if (isRefresh) {
            pageNo = 1
        }

        scopeNetLife {
            val data =
                Get<WanCategoryTypeBean>(ApiUrls.getWanCategoryType(pageNo)) {
                    param("cid", cid)
                    param("page_size ", pageSize.toString())
                }.await().data.datas

            //请求成功
            isFirstEmpty = data.isEmpty() && pageNo == 1
            for (i in data.indices) {
                //多布局：0：无图片，1：一张图
                data[i].itemType = when (data[i].envelopePic) {
                    "https://www.wanandroid.com/resources/image/pc/default_project_img.jpg",
                    "http://www.wanandroid.com/resources/image/pc/default_project_img.jpg",
                    "https://wanandroid.com/resources/image/pc/default_project_img.jpg",
                    "http://wanandroid.com/resources/image/pc/default_project_img.jpg" -> {
                        0
                    }
                    else -> {
                        1
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
                    listData = arrayListOf<CategoryTypeData>()
                )
            categoryTypeDataState.value = listDataUiState
        }
    }

}