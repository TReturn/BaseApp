package com.example.lib_main.model
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


/**
 * @CreateDate : 2021/8/2 22:40
 * @Author : 青柠
 * @Description :
 */
@Serializable
data class BooksDataBean(
    @SerialName("error_code")
    var errorCode: Int = 0,
    @SerialName("reason")
    var reason: String = "",
    @SerialName("result")
    var result: Result = Result(),
    @SerialName("resultcode")
    var resultcode: String = ""
) {
    @Serializable
    data class Result(
        @SerialName("data")
        var `data`: List<Data> = listOf(),
        @SerialName("pn")
        var pn: String = "",
        @SerialName("rn")
        var rn: String = "",
        @SerialName("totalNum")
        var totalNum: String = ""
    ) {
        @Serializable
        data class Data(
            @SerialName("bytime")
            var bytime: String = "",
            @SerialName("catalog")
            var catalog: String = "",
            @SerialName("img")
            var img: String = "",
            @SerialName("online")
            var online: String = "",
            @SerialName("reading")
            var reading: String = "",
            @SerialName("sub1")
            var sub1: String = "",
            @SerialName("sub2")
            var sub2: String = "",
            @SerialName("tags")
            var tags: String = "",
            @SerialName("title")
            var title: String = ""
        )
    }
}