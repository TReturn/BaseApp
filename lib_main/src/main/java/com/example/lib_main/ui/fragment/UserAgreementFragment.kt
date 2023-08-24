package com.example.lib_main.ui.fragment

import android.os.Bundle
import com.example.lib_base.base.BaseFragment
import com.example.lib_main.databinding.FragmentUserAgreementBinding
import com.example.lib_main.viewmodel.UserAgreementViewModel
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import me.hgj.jetpackmvvm.ext.nav

/**
 * @CreateDate: 2023/8/24 13:17
 * @Author: 青柠
 * @Description: 用户协议、隐私政策页面
 */
class UserAgreementFragment : BaseFragment<UserAgreementViewModel, FragmentUserAgreementBinding>() {

    private var title = "用户协议"

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        setTranslucent(mDatabind.flTranslucent)

        arguments?.run {
            title = getString("TITLE", "")
        }

        mDatabind.include.titleBar.title = title
        mDatabind.include.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                nav().navigateUp()
            }
        })
    }

}