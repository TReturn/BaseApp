package com.example.lib_main.model

import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


/**
 * @CreateDate : 2021/8/27 14:14
 * @Author : 青柠
 * @Description : 文章所有子分类
 */

@Serializable
data class WanCategoryBean(
    @SerialName("data")
    var `data`: List<Data> = listOf(),
    @SerialName("errorCode")
    var errorCode: Int = 0,
    @SerialName("errorMsg")
    var errorMsg: String = ""
) {
    @Serializable
    data class Data(
        @SerialName("children")
        var children: List<String> = listOf(),
        @SerialName("courseId")
        var courseId: Int = 0,
        @SerialName("id")
        var id: Int = 0,
        @SerialName("name")
        var name: String = "",
        @SerialName("order")
        var order: Int = 0,
        @SerialName("parentChapterId")
        var parentChapterId: Int = 0,
        @SerialName("userControlSetTop")
        var userControlSetTop: Boolean = false,
        @SerialName("visible")
        var visible: Int = 0
    )
}