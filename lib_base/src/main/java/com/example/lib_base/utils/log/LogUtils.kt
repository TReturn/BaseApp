package com.example.lib_base.utils.log

import com.example.lib_base.BuildConfig
import com.orhanobut.logger.Logger

/**
 * @CreateDate : 2021/8/25 21:29
 * @Author : 青柠
 * @Description : Logger日志打印
 */
object LogUtils {
    fun d(log: Any) {
        if (BuildConfig.DEBUG) {
            Logger.d(log)
        }
    }
}