package com.example.lib_main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.scopeNetLife
import com.drake.net.Get
import com.example.lib_base.constant.ApiUrls
import com.example.lib_main.model.WanCategoryBean
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate : 2021/8/27 14:28
 * @Author : 青柠
 * @Description :
 */
class ThirdViewModel : BaseViewModel() {

    //文章所有子分类集合数据
    var categoryDataState = MutableLiveData<List<WanCategoryBean.Data>>()

    private fun getCategoryData(){
        scopeNetLife {
            categoryDataState.value = Get<WanCategoryBean>(ApiUrls.WAN_CATEGORY) {
            }.await().data
        }
    }

    init {
        getCategoryData()
    }
}