package com.example.lib_main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.scopeNetLife
import com.drake.net.Get
import com.example.lib_base.constant.ApiUrls
import com.example.lib_main.model.CategoryTypeData
import com.example.lib_main.model.WanCategoryBean
import com.example.lib_main.model.WanCategoryTypeBean
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate : 2021/8/27 14:28
 * @Author : 青柠
 * @Description :
 */
class ProjectsMainViewModel : BaseViewModel() {

    private var pageNo = 1

    //文章所有子分类集合数据
    var categoryDataState = MutableLiveData<List<WanCategoryBean.Data>>()

    //具体子分类下的列表
    var categoryTypeDataState = MutableLiveData<List<CategoryTypeData>>()

    /**
     * 获取分类
     */
    fun getCategoryData() {
        scopeNetLife {
            categoryDataState.value = Get<WanCategoryBean>(ApiUrls.WAN_CATEGORY) {
            }.await().data
        }
    }

    /**
     * 获取具体子分类下的列表
     * @param isRefresh 是否刷新
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

            pageNo++
            categoryTypeDataState.value = data
        }.catch {
            // 协程内部发生错误回调, it为异常
            handleError(it)

        }
    }

}