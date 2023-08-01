package com.example.baseapp.viewmodel

import androidx.lifecycle.MutableLiveData
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate: 2022/6/21 10:06
 * @Author: 青柠
 * @Description:
 */
class MainViewModel : BaseViewModel() {

    //是否显示右下角可移动贴边View
    val isShowFruitView = MutableLiveData<Boolean>()

    init {
        isShowFruitView.value = false
    }
}