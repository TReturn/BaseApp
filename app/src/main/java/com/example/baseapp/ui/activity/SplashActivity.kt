package com.example.baseapp.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baseapp.BuildConfig
import com.example.baseapp.databinding.ActivitySplashBinding
import com.example.baseapp.viewmodel.SplashViewModel
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.constant.MMKVKeys
import com.example.lib_base.constant.RouterUrls
import com.example.lib_base.router.RouterUtils
import com.example.lib_base.utils.qmui.QMUIStatusBarHelper
import com.example.lib_base.utils.ui.TextFontUtils
import kotlinx.coroutines.*

@Route(path = RouterUrls.ROUTER_URL_SPLASH)
class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel

        //沉浸
        QMUIStatusBarHelper.translucent(this)
        //改变字体
        TextFontUtils.load(TextFontUtils.getDefaultTypeFace(),mDatabind.tvSplash)
        toMainActivity()
    }

    /**
     * 跳转到主页逻辑
     */

    private fun toMainActivity() {
        val waitTime: Long = if (BuildConfig.DEBUG) 200L else MMKVKeys.SPLASH_TIME
        //绑定lifecycle的协程
        lifecycleScope.launch {
            delay(waitTime)
            RouterUtils.intent(RouterUrls.ROUTER_URL_MAIN)
            finish()
        }
    }

    /**
     * 屏蔽物理返回按钮
     * @param keyCode
     * @param event
     * @return
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else super.onKeyDown(keyCode, event)
    }
}