package com.example.lib_base.utils.image

import androidx.fragment.app.FragmentActivity
import cc.shinichi.library.ImagePreview

/**
 * @CreateDate : 2021/8/18 20:50
 * @Author : 青柠
 * @Description : 大图浏览
 */
object BigImageUtils {

    /**
     * 加载多张大图
     * @param activity FragmentActivity
     * @param imageList MutableList<String>
     * @param index Int
     */
    fun show(
        activity: FragmentActivity,
        imageList: MutableList<String>,
        index: Int = 0,
        isShowDownLoad: Boolean = false
    ) {
        ImagePreview
            .instance
            // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好；
            .setContext(activity)
            // 设置从第几张开始看（索引从0开始）
            .setIndex(index)
            // 2：直接传url List
            .setImageList(imageList)
            .setShowDownButton(isShowDownLoad)
            // 开启预览
            .start()

    }

    /**
     * 加载单张大图
     * @param activity FragmentActivity
     * @param image String
     * @param isShowDownLoad Boolean
     */
    fun show(activity: FragmentActivity, image: String, isShowDownLoad: Boolean = false) {
        ImagePreview
            .instance
            // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好；
            .setContext(activity)
            // 设置从第几张开始看（索引从0开始）
            .setIndex(0)
            // 只有一张图片的情况，可以直接传入这张图片的url
            .setImage(image)
            .setShowDownButton(isShowDownLoad)
            .start()
    }
}