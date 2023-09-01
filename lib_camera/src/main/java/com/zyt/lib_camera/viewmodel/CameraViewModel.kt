package com.zyt.lib_camera.viewmodel

import androidx.lifecycle.MutableLiveData
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

class CameraViewModel : BaseViewModel() {


    val isButtonClickable = MutableLiveData(true)

}