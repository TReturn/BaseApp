package com.example.lib_main.viewmodel

import androidx.lifecycle.MutableLiveData
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

class CameraXViewModel : BaseViewModel() {

    val originalPic = MutableLiveData<String>()

    val compressPic = MutableLiveData<String>()

    init {
        originalPic.value = "原图"
        compressPic.value = "压缩"
    }
}