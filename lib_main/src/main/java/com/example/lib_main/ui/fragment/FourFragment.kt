package com.example.lib_main.ui.fragment

import android.os.Bundle
import com.example.lib_base.base.BaseFragment
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentThirdBinding
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate : 2020/12/31
 * @Author : 青柠
 * @Description :
 */
class FourFragment : BaseFragment<BaseViewModel, FragmentThirdBinding>() {

    override fun layoutId(): Int {
        return R.layout.fragment_third
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {

    }


}