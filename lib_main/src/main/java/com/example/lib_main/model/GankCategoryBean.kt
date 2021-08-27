package com.example.lib_main.model
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


/**
 * @CreateDate : 2021/8/27 14:14
 * @Author : 青柠
 * @Description : 文章所有子分类
 */
@Serializable
data class GankCategoryBean(
    @SerialName("data")
    var `data`: List<Data> = listOf(),
    @SerialName("status")
    var status: Int = 0
) {
    @Serializable
    data class Data(
        @SerialName("coverImageUrl")
        var coverImageUrl: String = "",
        @SerialName("desc")
        var desc: String = "",
        @SerialName("_id")
        var id: String = "",
        @SerialName("title")
        var title: String = "",
        @SerialName("type")
        var type: String = ""
    )
}