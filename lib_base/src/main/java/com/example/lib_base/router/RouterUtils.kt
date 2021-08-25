package com.example.lib_base.router

import android.app.Activity
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_base.constant.RouterUrls

/**
 * @CreateDate : 2020/12/31
 * @Author : 青柠
 * @Description : 阿里路由跳转封装
 */
object RouterUtils {

    /**
     * 应用内简单的跳转
     * @param router 路由地址
     */
    fun intent(router: String) {
        ARouter.getInstance().build(router).navigation()
    }

    /**
     * ActivityForResult跳转
     * @param path 路由地址
     * @param activity Activity
     * @param requestCode Int
     */
    fun intent(path: String, activity: Activity, requestCode:Int) {
        ARouter.getInstance().build(path).navigation(activity,requestCode)
    }


    /**
     * 跳转到Web
     * @param url 网页地址
     * @param title 标题
     */
    fun web(url: String, title: String = "") {
        ARouter.getInstance().build(RouterUrls.ROUTER_URL_WEB)
            .withString("webUrl", url)
            .withString("title", title)
            .navigation()
    }

}