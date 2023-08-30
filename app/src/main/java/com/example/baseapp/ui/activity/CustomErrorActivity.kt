package com.example.baseapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.blankj.utilcode.util.ClipboardUtils
import com.example.baseapp.R
import com.example.baseapp.databinding.ActivityCustomErrorBinding
import com.example.baseapp.viewmodel.CustomErrorViewModel
import com.example.lib_base.base.BaseActivity
import com.hjq.toast.Toaster

/**
 * @CreateDate: 2023/8/27 18:58
 * @Author: 青柠
 * @Description: 崩溃捕获的UI，可在此自定义
 */
class CustomErrorActivity : BaseActivity<CustomErrorViewModel, ActivityCustomErrorBinding>() {

    private val config by lazy { CustomActivityOnCrash.getConfigFromIntent(intent) }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()

    }

    private fun copyErrorToClipboard() {
        val errorInformation = CustomActivityOnCrash.getAllErrorDetailsFromIntent(
            this,
            intent
        )
        ClipboardUtils.copyText(errorInformation)
        Toaster.show(getString(R.string.error_copy_success))
    }

    inner class ProxyClick {
        fun toReStartApp() {
            config?.let {
                CustomActivityOnCrash.restartApplication(this@CustomErrorActivity, it)
            }
        }

        fun toSeeLog() {
            val dialog = AlertDialog.Builder(this@CustomErrorActivity)
                .setTitle(getString(R.string.error_info))
                .setMessage(
                    CustomActivityOnCrash.getAllErrorDetailsFromIntent(
                        this@CustomErrorActivity,
                        intent
                    )
                )
                .setPositiveButton(
                    getString(R.string.error_close),
                    null
                )
                .setNeutralButton(
                    getString(R.string.error_copy)
                ) { dialog1, which -> copyErrorToClipboard() }
                .show()

            //复制、关闭按钮颜色
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(getColor(R.color.text_article_source))
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
                .setTextColor(getColor(R.color.text_article_source))
        }
    }
}