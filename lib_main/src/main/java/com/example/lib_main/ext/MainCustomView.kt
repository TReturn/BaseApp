package com.example.lib_main.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.lib_main.R
import com.example.lib_main.ui.fragment.FourFragment
import com.example.lib_main.ui.fragment.MainFragment
import com.example.lib_main.ui.fragment.SecondFragment
import com.example.lib_main.ui.fragment.ThirdFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * @CreateDate : 2021/8/8 0:18
 * @Author : 青柠
 * @Description :
 */

/**
 * 首页底部导航栏ViewPager2初始化
 * @receiver ViewPager2
 * @param fragment FragmentActivity
 * @return ViewPager2
 */
fun ViewPager2.initMain(
    fragment: FragmentActivity,
    bottomNavigationView: BottomNavigationView,
    isUserInputEnabled: Boolean
): ViewPager2 {
    //是否可滑动
    this.offscreenPageLimit = 4
    //设置适配器
    adapter = object : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> {
                    return MainFragment()
                }
                1 -> {
                    return SecondFragment()
                }
                2 -> {
                    return ThirdFragment()
                }
                3 -> {
                    return FourFragment()
                }
                else -> {
                    return MainFragment()
                }
            }
        }

        override fun getItemCount() = 4
    }
    if (isUserInputEnabled) {
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        bottomNavigationView.selectedItemId = R.id.navigation_main
                    }
                    1 -> {
                        bottomNavigationView.selectedItemId = R.id.navigation_second
                    }
                    2 -> {
                        bottomNavigationView.selectedItemId = R.id.navigation_third
                    }
                    else -> {
                        bottomNavigationView.selectedItemId = R.id.navigation_four
                    }
                }
            }
        })
    }
    return this
}