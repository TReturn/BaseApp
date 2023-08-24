package com.example.lib_main.viewmodel

import androidx.lifecycle.MutableLiveData
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate : 2021/7/29
 * @Author : 青柠
 * @Description :
 */
class SecondViewModel : BaseViewModel() {
    //是否显示右下角可移动贴边View
    val isShowFruitView = MutableLiveData<Boolean>()

    init {
        isShowFruitView.value = true
    }

}