package com.example.lib_main.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.lib_base.base.BaseFragment
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentMainBinding
import com.example.lib_main.viewmodel.MainViewModel


/**
 * @CreateDate : 2020/12/31
 * @Author : 青柠
 * @Description :
 */
class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {


    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click = ProxyClick()
        mDatabind.vm = mViewModel

    }

    override fun initData() {

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun createObserver() {

    }


    inner class ProxyClick {
        fun toScan() {

        }
    }

}

