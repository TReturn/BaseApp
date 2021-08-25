package com.example.lib_base.utils.ui

import android.app.Activity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb

/**
 * @CreateDate : 2021/8/16 19:09
 * @Author : 青柠
 * @Description : 依附于当前Activity的ViewGroup加载Web
 */
object WebUtils {
    /**
     *
     * @param context Activity
     * @param viewGroup ViewGroup
     * @param url String
     */
    fun load(context: Activity, viewGroup: ViewGroup, url: String) {
        AgentWeb.with(context)
            .setAgentWebParent(viewGroup, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(url)
    }
}