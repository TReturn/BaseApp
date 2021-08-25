package com.example.lib_main.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.constant.RouterUrls
import com.example.lib_base.ext.interceptLongClick
import com.example.lib_main.R
import com.example.lib_main.databinding.ActivityMainBinding
import com.example.lib_main.ext.initMain
import com.example.lib_main.ui.fragment.FourFragment
import com.example.lib_main.ui.fragment.MainFragment
import com.example.lib_main.ui.fragment.SecondFragment
import com.example.lib_main.ui.fragment.ThirdFragment
import com.example.lib_main.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hjq.toast.ToastUtils
import java.util.*


@Route(path = RouterUrls.ROUTER_URL_MAIN)
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    private var firstTime = 0L
    private var delayTime = 2000L


    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {

        mDatabind.bottomNavigation.itemIconTintList = null

        //初始化显示内容
        val fragmentList: MutableList<Fragment> = ArrayList()
        val titleList: MutableList<String> = ArrayList()

        fragmentList.add(MainFragment())
        fragmentList.add(SecondFragment())
        fragmentList.add(ThirdFragment())
        fragmentList.add(FourFragment())

        //初始化viewpager2
        mDatabind.viewPager.initMain(this)

        //当 ViewPager 滑动后设置BottomNavigationView 选中相应选项
//        mDatabind.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//
//                mDatabind.bottomNavigation.menu.getItem(position).isChecked = true
//            }
//
//            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
//            override fun onPageScrollStateChanged(state: Int) {}
//        })


        //给底部导航栏菜单项添加点击事件
        mDatabind.bottomNavigation.setOnNavigationItemSelectedListener(
            mOnNavigationItemSelectedListener
        )

        //拦截BottomNavigation长按事件 防止长按时出现Toast
        mDatabind.bottomNavigation.interceptLongClick(
            R.id.navigation_main,
            R.id.navigation_second,
            R.id.navigation_third,
            R.id.navigation_four
        )
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_main -> {
                    mDatabind.viewPager.currentItem = 0
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_second -> {
                    mDatabind.viewPager.currentItem = 1
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_third -> {
                    mDatabind.viewPager.currentItem = 2
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_four -> {
                    mDatabind.viewPager.currentItem = 3
                    return@OnNavigationItemSelectedListener true
                }
                else -> {
                }
            }
            false
        }

    /**
     * 主页按两次返回键退出应用
     * @param keyCode Int
     * @param event KeyEvent
     * @return Boolean
     */
    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
            val secondTime = System.currentTimeMillis()
            if (secondTime - firstTime > delayTime) {
                ToastUtils.show(resources.getString(R.string.main_finish))
                firstTime = secondTime
                return true
            } else {
                finish()
            }
        }
        return super.onKeyUp(keyCode, event)
    }


}