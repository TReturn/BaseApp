package com.example.lib_main.viewmodel

import androidx.lifecycle.MutableLiveData
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate : 2021/8/11 23:35
 * @Author : 青柠
 * @Description :
 */
class SearchViewModel : BaseViewModel() {
    //搜索框内容
    val searchContent = MutableLiveData<String>()

    //是否显示搜索历史UI
    val isShowSearchHistory = MutableLiveData<Boolean>()

    //是否显示清除输入框按钮
    val isShowDeleteSearchContent = MutableLiveData<Boolean>()


    init {
        isShowSearchHistory.value = true
    }
}