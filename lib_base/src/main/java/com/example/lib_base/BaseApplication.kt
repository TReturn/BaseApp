package com.example.lib_base

import android.app.Application
import android.app.ProgressDialog
import android.content.Context
import android.view.Gravity
import com.alibaba.android.arouter.launcher.ARouter
import com.drake.net.NetConfig
import com.drake.net.interceptor.LogRecordInterceptor
import com.drake.net.interceptor.RequestInterceptor
import com.drake.net.interfaces.NetErrorHandler
import com.drake.net.okhttp.*
import com.drake.net.request.BaseRequest
import com.example.lib_base.constant.ApiUrls
import com.example.lib_base.constant.SdkKeys
import com.example.lib_base.net.SerializationConverter
import com.hjq.toast.ToastUtils
import com.hjq.toast.style.WhiteToastStyle
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV
import java.util.concurrent.TimeUnit


/**
 * @CreateDate : 2020/12/30
 * @Author : 青柠
 * @Description :
 */
open class BaseApplication : Application() {

    //将Application 单例化，可供全局调用 Context
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        //MMKV键值对存储
        MMKV.initialize(this)

        //阿里路由
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)

        //Bugly崩溃上报
        CrashReport.initCrashReport(this, SdkKeys.BUGLY_KEY, BuildConfig.DEBUG)

        //Toast初始化
        ToastUtils.init(this)
        ToastUtils.setGravity(Gravity.CENTER)
        ToastUtils.setStyle(WhiteToastStyle())

        //日志打印初始化
        Logger.addLogAdapter(AndroidLogAdapter())

        //协程网络请求库初始化
        NetConfig.init(ApiUrls.BASE_GANK_URL) {

            setConverter(SerializationConverter())

            // 超时设置
            connectTimeout(2, TimeUnit.MINUTES)
            readTimeout(2, TimeUnit.MINUTES)
            writeTimeout(2, TimeUnit.MINUTES)

            setLog(BuildConfig.DEBUG) // LogCat异常日志
            addInterceptor(LogRecordInterceptor(BuildConfig.DEBUG)) // 添加日志记录器
            setRequestInterceptor(object : RequestInterceptor { // 添加请求拦截器
                override fun interceptor(request: BaseRequest) {
                    request.addHeader("client", "Net")
                }
            })

            //全局请求错误处理
            setErrorHandler(object : NetErrorHandler {
                override fun onError(e: Throwable) {
                    ToastUtils.show(e.message)
                }
            })

            setDialogFactory { // 全局加载对话框
                ProgressDialog(it).apply {
                    setMessage("我是全局自定义的加载对话框...")
                }
            }
        }

        //刷新控件初始化
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(
                R.color.color_refresh_bg,
                R.color.color_refresh_text
            ) //全局设置主题颜色
            //指定为经典Header，默认是 贝塞尔雷达Header
            MaterialHeader(context)
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }
    
}