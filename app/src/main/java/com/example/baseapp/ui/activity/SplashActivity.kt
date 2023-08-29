package com.example.baseapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.lifecycle.lifecycleScope
import com.example.baseapp.BuildConfig
import com.example.baseapp.databinding.ActivitySplashBinding
import com.example.baseapp.ui.dialog.PrivacyDialog
import com.example.baseapp.ui.dialog.PrivacyRetentionDialog
import com.example.baseapp.viewmodel.SplashViewModel
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.constant.UserKeys
import com.example.lib_base.utils.data.MMKVUtils
import com.example.lib_base.utils.qmui.QMUIStatusBarHelper
import com.example.lib_base.utils.ui.TextFontUtils
import com.lxj.xpopup.XPopup
import kotlinx.coroutines.*


class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel

        //沉浸
        QMUIStatusBarHelper.translucent(this)
        //改变字体
        TextFontUtils.load(TextFontUtils.getDefaultTypeFace(), mDatabind.tvSplash)

        checkPrivacy()
    }

    /**
     * 检测隐私协议
     */
    private fun checkPrivacy() {
        if (MMKVUtils.getBool(UserKeys.IS_AGREE_PRIVACY)) {
            toMainActivity()
        } else {
            XPopup.Builder(this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .asCustom(PrivacyDialog(this, {
                    //已同意，进入首页逻辑
                    MMKVUtils.put(UserKeys.IS_AGREE_PRIVACY, true)
                    toMainActivity()
                }) {
                    //不同意，结束APP
                    finish()
                }).show()
        }
    }

    /**
     * 跳转到主页逻辑
     */

    private fun toMainActivity() {
        val waitTime: Long = if (BuildConfig.DEBUG) 200L else UserKeys.SPLASH_TIME
        //绑定lifecycle的协程
        lifecycleScope.launch {
            delay(waitTime)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
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