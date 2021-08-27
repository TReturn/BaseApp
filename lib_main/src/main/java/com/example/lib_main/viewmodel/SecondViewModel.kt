package com.example.lib_main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.scopeNetLife
import com.drake.net.Get
import com.example.lib_base.constant.ApiUrls
import com.example.lib_base.net.GankSerializationConverter
import com.example.lib_main.model.GankBannerBean
import com.example.lib_main.model.GankCategoryBean
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate : 2021/8/27 14:28
 * @Author : 青柠
 * @Description :
 */
class SecondViewModel : BaseViewModel() {

    //文章所有子分类集合数据
    var categoryDataState = MutableLiveData<List<GankCategoryBean.Data>>()

    private fun getCategoryData(){
        scopeNetLife {
            categoryDataState.value = Get<GankCategoryBean>(ApiUrls.GANK_CATEGORY) {
            }.await().data
        }
    }

    init {
        getCategoryData()
    }
}