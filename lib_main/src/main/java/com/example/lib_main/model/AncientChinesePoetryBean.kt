package com.example.lib_main.model
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


/**
 * @CreateDate: 2021/12/3 3:36 下午
 * @Author: 青柠
 * @Description:
 */

 @Serializable
data class AncientChinesePoetryBean(
    @SerialName("data")
    var `data`: Data,
    @SerialName("ipAddress")
    var ipAddress: String,
    @SerialName("status")
    var status: String,
    @SerialName("token")
    var token: String,
    @SerialName("warning")
    var warning: String
) {
    @Serializable
    data class Data(
        @SerialName("cacheAt")
        var cacheAt: String,
        @SerialName("content")
        var content: String,
        @SerialName("id")
        var id: String,
        @SerialName("matchTags")
        var matchTags: List<String>,
        @SerialName("origin")
        var origin: Origin,
        @SerialName("popularity")
        var popularity: Int,
        @SerialName("recommendedReason")
        var recommendedReason: String
    ) {
        @Serializable
        data class Origin(
            @SerialName("author")
            var author: String,
            @SerialName("content")
            var content: List<String>,
            @SerialName("dynasty")
            var dynasty: String,
            @SerialName("title")
            var title: String,
            @SerialName("translate")
            var translate: List<String>
        )
    }
}