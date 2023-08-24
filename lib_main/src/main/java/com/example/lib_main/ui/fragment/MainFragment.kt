package com.example.lib_main.ui.fragment

import android.os.Bundle
import com.example.lib_base.appViewModel
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.ext.interceptLongClick
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentMainBinding
import com.example.lib_main.databinding.FragmentThirdBinding
import com.example.lib_main.ext.initMain
import com.example.lib_main.viewmodel.MainViewModel
import com.example.lib_main.viewmodel.ThirdViewModel
import com.google.android.material.navigation.NavigationBarView

/**
 * @CreateDate: 2023/8/24 17:10
 * @Author: 青柠
 * @Description: 项目首页
 */
class MainFragment: BaseFragment<MainViewModel, FragmentMainBinding>() {

    private val tabs = arrayOf(
        R.id.navigation_main,
        R.id.navigation_second,
        R.id.navigation_third,
        R.id.navigation_fourth,
    )
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel

        //修复切换底部导航栏时Icon未改变
        mDatabind.bottomNavigation.itemIconTintList = null

        //初始化viewpager2
        mDatabind.viewPager.initMain(mActivity, mDatabind.bottomNavigation, false)

        //给底部导航栏菜单项添加点击事件
        mDatabind.bottomNavigation.setOnItemSelectedListener(mItemSelectedListener)

        //拦截BottomNavigation长按事件 防止长按时出现Toast
        mDatabind.bottomNavigation.interceptLongClick(tabs)
    }

    override fun createObserver() {
        super.createObserver()
        //跳转到首页Tab
        appViewModel.intentToMainTab.observeInFragment(this) {
            if (it < tabs.size) {
                mDatabind.viewPager.setCurrentItem(it, false)
                mDatabind.bottomNavigation.selectedItemId = tabs[it]
            }

        }
    }

    private val mItemSelectedListener: NavigationBarView.OnItemSelectedListener =
        NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_main -> {
                    mDatabind.viewPager.setCurrentItem(0,false)
                    return@OnItemSelectedListener true
                }

                R.id.navigation_second -> {
                    mDatabind.viewPager.setCurrentItem(1,false)
                    return@OnItemSelectedListener true
                }

                R.id.navigation_third -> {
                    mDatabind.viewPager.setCurrentItem(2,false)
                    return@OnItemSelectedListener true
                }

                R.id.navigation_fourth -> {
                    mDatabind.viewPager.setCurrentItem(3,false)
                    return@OnItemSelectedListener true
                }
            }
            return@OnItemSelectedListener false
        }

}