package com.example.lib_base.utils.banner

import com.stx.xhb.androidx.entity.SimpleBannerInfo

/**
 * @CreateDate : 2021/8/1 2:53
 * @Author : 青柠
 * @Description :
 */
class BaseBannerData : SimpleBannerInfo() {
    override fun getXBannerUrl(): Any {
        return image
    }

    override fun getXBannerTitle(): String {
        return title
    }

    private var image: String = ""
    private var url: String = ""
    private var title: String = ""

    fun getImage(): String {
        return image
    }

    fun setImage(image: String) {
        this.image = image
    }

    fun getUrl(): String {
        return url
    }

    fun setUrl(url: String) {
        this.url = url
    }

    fun getTitle(): String {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }
}