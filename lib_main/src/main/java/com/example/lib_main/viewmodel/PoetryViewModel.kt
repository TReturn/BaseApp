package com.example.lib_main.viewmodel

import androidx.lifecycle.MutableLiveData
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate: 2023/3/31 18:28
 * @Author: 青柠
 * @Description:
 */
class PoetryViewModel : BaseViewModel() {

    //诗名
    val poetryTitle = MutableLiveData<String>()

    //作者
    val poetryAuthor = MutableLiveData<String>()

    //内容
    val poetryContent = MutableLiveData<String>()

    //释义
    val poetryTranslate = MutableLiveData<String>()

}