package com.example.lib_base.web

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_base.R
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.constant.RouterUrls
import com.example.lib_base.databinding.ActivityWebBinding
import com.example.lib_base.utils.log.LogUtils
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.just.agentweb.AgentWeb
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate : 2021/8/1
 * @Author : 青柠
 * @Description : Web
 */
@Route(path = RouterUrls.ROUTER_URL_WEB)
class WebActivity : BaseActivity<BaseViewModel, ActivityWebBinding>() {

    @Autowired
    lateinit var webUrl: String

    @Autowired
    lateinit var title: String

    override fun initView(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)

        if (!TextUtils.isEmpty(title)) {
            mDatabind.include.titleBar.title = title
        }

        //标题栏点击事件
        mDatabind.include.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                finish()
            }
        })

        AgentWeb.with(this)
            .setAgentWebParent(mDatabind.llWeb, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(webUrl)

        LogUtils.d("WEB:$webUrl")
    }
}