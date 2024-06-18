package com.example.lib_base

import android.app.Application
import android.app.ProgressDialog
import android.content.Context
import android.view.Gravity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.DeviceUtils
import com.drake.net.NetConfig
import com.drake.net.interceptor.LogRecordInterceptor
import com.drake.net.interceptor.RequestInterceptor
import com.drake.net.interfaces.NetErrorHandler
import com.drake.net.okhttp.*
import com.drake.net.request.BaseRequest
import com.example.lib_base.constant.ApiUrls
import com.example.lib_base.constant.SdkKeys
import com.example.lib_base.constant.UserKeys
import com.example.lib_base.event.AppViewModel
import com.example.lib_base.net.WanSerializationConverter
import com.example.lib_base.utils.data.MMKVUtils
import com.example.lib_base.utils.log.LogUtils
import com.hjq.language.MultiLanguages
import com.hjq.toast.Toaster
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

val appViewModel: AppViewModel by lazy { BaseApplication.appViewModelInstance }

open class BaseApplication : Application(), ViewModelStoreOwner {


    private var mFactory: ViewModelProvider.Factory? = null
    private lateinit var mAppViewModelStore: ViewModelStore

    //将Application 单例化，可供全局调用 Context
    companion object {
        lateinit var context: BaseApplication
        lateinit var appViewModelInstance: AppViewModel
    }

    override fun onCreate() {
        super.onCreate()

        context = this

        mAppViewModelStore = ViewModelStore()
        appViewModelInstance = getAppViewModelProvider()[AppViewModel::class.java]

        //监听应用进入前台后台监听
        ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationObserver())

        //MMKV键值对存储
        MMKV.initialize(this)

        //1：日间模式，2：夜间模式，3：跟随系统。
        AppCompatDelegate.setDefaultNightMode(MMKVUtils.getInt(UserKeys.NIGHT_MODE, 1))

        //Bugly崩溃上报
        //设置开发设备
        CrashReport.setIsDevelopmentDevice(this, BuildConfig.DEBUG)

        //配置参数
        val strategy = CrashReport.UserStrategy(this).apply {
            //型号
            deviceModel = "${DeviceUtils.getManufacturer()} ${DeviceUtils.getModel()}"
            //版本号
            appVersion = AppUtils.getAppVersionName()
            //渠道号
            appChannel = "myChannel"
            //包名
            appPackageName = AppUtils.getAppPackageName()
        }
        CrashReport.initCrashReport(this, SdkKeys.BUGLY_KEY, BuildConfig.DEBUG, strategy)


        //Toast初始化
        Toaster.init(this)
        Toaster.setGravity(Gravity.CENTER)
        Toaster.setStyle(WhiteToastStyle())

        //日志打印初始化
        Logger.addLogAdapter(AndroidLogAdapter())

        //协程网络请求库初始化
        NetConfig.initialize(ApiUrls.BASE_WAN_URL, this) {

            setConverter(WanSerializationConverter())

            // 超时设置
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)

            // LogCat异常日志
            setDebug(BuildConfig.DEBUG)
            // 添加日志记录器
            addInterceptor(LogRecordInterceptor(BuildConfig.DEBUG))
            // 添加请求拦截器
            setRequestInterceptor(object : RequestInterceptor {
                override fun interceptor(request: BaseRequest) {
                    request.addHeader("client", "Net")
                }
            })

            if (BuildConfig.DEBUG) {
                // 信任所有证书
                trustSSLCertificate()
            }

            //全局请求错误处理
            setErrorHandler(object : NetErrorHandler {
                override fun onError(e: Throwable) {
                    Toaster.show(e.message)
                    LogUtils.d("请求出错：\nMESSAGE:${e.message}\nURL:${e.localizedMessage}")
                }
            })

            setDialogFactory {
                // 全局加载对话框
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

        // 初始化语种切换框架
        MultiLanguages.init(this)

    }

    override fun attachBaseContext(base: Context) {
        // 绑定语种
        super.attachBaseContext(MultiLanguages.attach(base))
    }

    /**
     * 获取一个全局的ViewModel
     */
    private fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, this.getAppFactory())
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }
    

    override val viewModelStore: ViewModelStore
        get() = mAppViewModelStore

}