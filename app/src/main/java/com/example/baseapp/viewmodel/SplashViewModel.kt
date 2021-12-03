package com.example.baseapp.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.scopeNetLife
import com.drake.net.Get
import com.example.baseapp.model.AncientChinesePoetryBean
import com.example.baseapp.model.AncientChinesePoetryTokenBean
import com.example.lib_base.constant.ApiUrls
import com.example.lib_base.constant.MMKVKeys
import com.example.lib_base.constant.SdkKeys
import com.example.lib_base.net.GankSerializationConverter
import com.example.lib_base.net.PoetrySerializationConverter
import com.example.lib_base.net.SerializationConverter
import com.example.lib_base.utils.data.MMKVUtils
import com.example.lib_base.utils.log.LogUtils
import com.example.lib_main.model.IPLocationBean
import com.example.lib_main.model.WeatherBean
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate: 2021/12/3 2:57 下午
 * @Author: 青柠
 * @Description:
 */
class SplashViewModel : BaseViewModel() {

    //古诗返回结果
    val poetryResultDataState = MutableLiveData<AncientChinesePoetryBean.Data>()
    val poetryResult = MutableLiveData<String>()

    /**
     * 获取古诗Token
     */
    private fun getAncientChinesePoetryToken() {
        scopeNetLife {
            val data = Get<AncientChinesePoetryTokenBean>(ApiUrls.ANCIENT_CHINESE_POETRY_TOKEN) {
                converter = PoetrySerializationConverter()

            }.await().data

            MMKVUtils.set(MMKVKeys.POETRY_TOKEN, data)
        }
    }

    /**
     * 获取古诗
     */
    private fun getAncientChinesePoetry() {
        scopeNetLife {
            val data = Get<AncientChinesePoetryBean>(ApiUrls.ANCIENT_CHINESE_POETRY) {
                converter = PoetrySerializationConverter()
                //setHeader("X-User-Token",MMKVUtils.getString(MMKVKeys.POETRY_TOKEN))
                setHeader("X-User-Token","8XbBmwRp/0JMGC4ae27ILvcQoetBhnW8")
            }.await().data

            LogUtils.d(data)
        }
    }

    init {
        if (TextUtils.isEmpty(MMKVUtils.getString(MMKVKeys.POETRY_TOKEN))) {
            getAncientChinesePoetryToken()
        } else {
            getAncientChinesePoetry()
        }

    }
}