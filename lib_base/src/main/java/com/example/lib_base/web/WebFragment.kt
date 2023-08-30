package com.example.lib_base.web

import android.os.Bundle
import android.widget.LinearLayout
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.databinding.FragmentWebBinding
import com.example.lib_base.utils.log.LogUtils
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.just.agentweb.AgentWeb
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.nav

/**
 * @CreateDate : 2021/8/1
 * @Author : 青柠
 * @Description : Web
 */

class WebFragment : BaseFragment<BaseViewModel, FragmentWebBinding>() {

    private var webUrl = ""

    //0Fragment传入，1Activity传入
    private var type = 0
    override fun initView(savedInstanceState: Bundle?) {
        setTranslucent(mDatabind.flTranslucent)

        arguments?.run {
            val title = getString("TITLE", "")
            webUrl = getString("URL", "")
            type = getInt("TYPE", 0)
            mDatabind.include.titleBar.title = title
        }

        //标题栏点击事件
        mDatabind.include.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                if (type == 0) {
                    nav().navigateUp()
                } else {
                    activity?.finish()
                }

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