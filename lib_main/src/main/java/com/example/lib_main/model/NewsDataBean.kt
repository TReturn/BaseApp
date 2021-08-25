package com.example.lib_main.model

import com.chad.library.adapter.base.entity.MultiItemEntity
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


/**
 * @CreateDate : 2021/7/31 23:36
 * @Author : 青柠
 * @Description :
 */
@Serializable
data class NewsDataBean(
    @SerialName("error_code")
    var errorCode: Int,
    @SerialName("reason")
    var reason: String,
    @SerialName("result")
    var result: Result
) {
    @Serializable
    data class Result(
        @SerialName("data")
        var `data`: List<Data>,
        @SerialName("page")
        var page: String,
        @SerialName("pageSize")
        var pageSize: String,
        @SerialName("stat")
        var stat: String
    ) {
        @Serializable
        data class Data(
            @SerialName("author_name")
            var authorName: String,
            @SerialName("category")
            var category: String,
            @SerialName("date")
            var date: String,
            @SerialName("is_content")
            var isContent: String,
            @SerialName("thumbnail_pic_s")
            var thumbnailPicS: String,
            @SerialName("thumbnail_pic_s02")
            var thumbnailPicS02: String = "",
            @SerialName("thumbnail_pic_s03")
            var thumbnailPicS03: String = "",
            @SerialName("title")
            var title: String,
            @SerialName("uniquekey")
            var uniquekey: String,
            @SerialName("url")
            var url: String,
            override var itemType: Int = 1
        ):MultiItemEntity
    }
}