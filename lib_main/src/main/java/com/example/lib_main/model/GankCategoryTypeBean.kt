package com.example.lib_main.model
import com.chad.library.adapter.base.entity.MultiItemEntity
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


/**
 * @CreateDate : 2021/8/27 14:15
 * @Author : 青柠
 * @Description : 具体子分类下的列表
 */

 @Serializable
data class GankCategoryTypeBean(
    @SerialName("data")
    var `data`: List<Data> = listOf(),
    @SerialName("page")
    var page: Int = 0,
    @SerialName("page_count")
    var pageCount: Int = 0,
    @SerialName("status")
    var status: Int = 0,
    @SerialName("total_counts")
    var totalCounts: Int = 0
) {
    @Serializable
    data class Data(
        @SerialName("author")
        var author: String = "",
        @SerialName("category")
        var category: String = "",
        @SerialName("createdAt")
        var createdAt: String = "",
        @SerialName("desc")
        var desc: String = "",
        @SerialName("_id")
        var id: String = "",
        @SerialName("images")
        var images: MutableList<String> = ArrayList(),
        @SerialName("likeCounts")
        var likeCounts: Int = 0,
        @SerialName("publishedAt")
        var publishedAt: String = "",
        @SerialName("stars")
        var stars: Int = 0,
        @SerialName("title")
        var title: String = "",
        @SerialName("type")
        var type: String = "",
        @SerialName("url")
        var url: String = "",
        @SerialName("views")
        var views: Int = 0,
        override var itemType: Int = 0
    ):MultiItemEntity
}