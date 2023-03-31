package com.example.lib_main.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.constant.RouterUrls
import com.example.lib_main.R
import com.example.lib_main.databinding.ActivityPoetryBinding
import com.example.lib_main.viewmodel.PoetryViewModel
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar

/**
 * @CreateDate: 2023/3/31 18:25
 * @Author: 青柠
 * @Description: 诗歌详情页
 */
@Route(path = RouterUrls.ROUTER_URL_POETRY)
class PoetryActivity :
    BaseActivity<PoetryViewModel, ActivityPoetryBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()

        mDatabind.include.titleBar.title = getString(R.string.poetry_title)
        mDatabind.include.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                finish()
            }
        })
    }

    inner class ProxyClick {

    }



}