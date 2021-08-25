package com.example.lib_main.model
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


/**
 * @CreateDate : 2021/8/1 2:46
 * @Author : 青柠
 * @Description :
 */
@Serializable
data class GankBannerBean(
    @SerialName("data")
    var `data`: List<Data>,
    @SerialName("status")
    var status: Int
) {
    @Serializable
    data class Data(
        @SerialName("image")
        var image: String,
        @SerialName("title")
        var title: String,
        @SerialName("url")
        var url: String
    )
}