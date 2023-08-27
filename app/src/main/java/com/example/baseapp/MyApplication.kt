package com.example.baseapp

import cat.ereza.customactivityoncrash.config.CaocConfig
import com.example.baseapp.ui.activity.SplashActivity
import com.example.lib_base.BaseApplication
import com.example.baseapp.ui.activity.CustomErrorActivity

/**
 * @CreateDate : 2020/12/30
 * @Author : 青柠
 * @Description :
 */
class MyApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()

        /**
         * 初始化崩溃捕获
         * CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM：即使应用程序在后台运行，也会启动错误活动。
         * CaocConfig.BACKGROUND_MODE_CRASH：当应用程序在后台运行时，启动默认系统错误。
         * CaocConfig.BACKGROUND_MODE_SILENT：当应用程序在后台运行时，它会以静默方式崩溃。
         */
        CaocConfig.Builder.create()
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT)
            .enabled(true) //是否启用 default: true
            .showErrorDetails(true) //default: true 隐藏错误活动中的“错误详细信息”按钮
            .showRestartButton(true) //default: true 是否可以重启页面
            .logErrorOnRestart(true) //default: true
            .trackActivities(true) //default: false
            .minTimeBetweenCrashesMs(2000) //default: 3000
            .errorDrawable(R.drawable.ic_custom_error) //default: bug image
            //默认程序崩溃时重启的的activity default: null (your app's launch activity)
            .restartActivity(SplashActivity::class.java)
            //默认程序崩溃时跳转的(DefaultErrorActivity，自定义CustomErrorActivity)
            .errorActivity(CustomErrorActivity::class.java)
            .eventListener(null) //default: null
            .apply()
    }
}