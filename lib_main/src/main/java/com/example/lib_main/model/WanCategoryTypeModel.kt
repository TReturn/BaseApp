package com.example.lib_main.model

import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


/**
 * @CreateDate : 2021/8/27 14:15
 * @Author : 青柠
 * @Description : 具体子分类下的列表
 */


@Serializable
data class WanCategoryTypeBean(
    @SerialName("data")
    var `data`: Data = Data(),
    @SerialName("errorCode")
    var errorCode: Int = 0,
    @SerialName("errorMsg")
    var errorMsg: String = ""
) {
    @Serializable
    data class Data(
        @SerialName("curPage")
        var curPage: Int = 0,
        @SerialName("datas")
        var datas: List<CategoryTypeData> = listOf(),
        @SerialName("offset")
        var offset: Int = 0,
        @SerialName("over")
        var over: Boolean = false,
        @SerialName("pageCount")
        var pageCount: Int = 0,
        @SerialName("size")
        var size: Int = 0,
        @SerialName("total")
        var total: Int = 0
    )
}

@Serializable
data class CategoryTypeData(
    @SerialName("apkLink")
    var apkLink: String = "",
    @SerialName("audit")
    var audit: Int = 0,
    @SerialName("author")
    var author: String = "",
    @SerialName("canEdit")
    var canEdit: Boolean = false,
    @SerialName("chapterId")
    var chapterId: Int = 0,
    @SerialName("chapterName")
    var chapterName: String = "",
    @SerialName("collect")
    var collect: Boolean = false,
    @SerialName("courseId")
    var courseId: Int = 0,
    @SerialName("desc")
    var desc: String = "",
    @SerialName("descMd")
    var descMd: String = "",
    @SerialName("envelopePic")
    var envelopePic: String = "",
    @SerialName("fresh")
    var fresh: Boolean = false,
    @SerialName("host")
    var host: String = "",
    @SerialName("id")
    var id: Int = 0,
    @SerialName("link")
    var link: String = "",
    @SerialName("niceDate")
    var niceDate: String = "",
    @SerialName("niceShareDate")
    var niceShareDate: String = "",
    @SerialName("origin")
    var origin: String = "",
    @SerialName("prefix")
    var prefix: String = "",
    @SerialName("projectLink")
    var projectLink: String = "",
    @SerialName("publishTime")
    var publishTime: Long = 0,
    @SerialName("realSuperChapterId")
    var realSuperChapterId: Int = 0,
    @SerialName("selfVisible")
    var selfVisible: Int = 0,
    @SerialName("shareDate")
    var shareDate: Long = 0,
    @SerialName("shareUser")
    var shareUser: String = "",
    @SerialName("superChapterId")
    var superChapterId: Int = 0,
    @SerialName("superChapterName")
    var superChapterName: String = "",
    @SerialName("tags")
    var tags: List<Tag> = listOf(),
    @SerialName("title")
    var title: String = "",
    @SerialName("type")
    var type: Int = 0,
    @SerialName("userId")
    var userId: Int = 0,
    @SerialName("visible")
    var visible: Int = 0,
    @SerialName("zan")
    var zan: Int = 0,
    var itemType: Int = 0
)  {
    @Serializable
    data class Tag(
        @SerialName("name")
        var name: String = "",
        @SerialName("url")
        var url: String = ""
    )
}