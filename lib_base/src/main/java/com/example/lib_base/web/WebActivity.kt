package com.example.lib_base.web

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.lib_base.R
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.databinding.ActivityWebBinding
import com.example.lib_base.utils.qmui.QMUIStatusBarHelper
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate : 2023/8/29
 * @Author : 青柠
 * @Description : 无法使用Navigation的情况下，可使用此WebActivity
 */
class WebActivity : BaseActivity<BaseViewModel, ActivityWebBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

        QMUIStatusBarHelper.translucent(this)

        val webFragment = WebFragment().apply {
            arguments = intent.extras
        }
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContent, webFragment)
                .commit()
        }
    }

    /**
     * 打开WEB
     */
    fun start(context: Context?, title: String, url: String) {
        context?.let {
            it.startActivity(Intent(it, WebActivity::class.java).apply {
                putExtra("TITLE", title)
                putExtra("URL", url)
                putExtra("TYPE", 1)
            })
        }
    }

}