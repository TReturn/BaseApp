package com.example.baseapp.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baseapp.databinding.ActivityMainBinding
import com.example.baseapp.viewmodel.MainViewModel
import com.example.lib_base.appViewModel
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.constant.RouterUrls
import com.example.lib_base.ext.interceptLongClick
import com.example.lib_base.utils.qmui.QMUIStatusBarHelper
import com.example.lib_base.widget.SlideImageView
import com.example.lib_main.R
import com.example.lib_main.ext.initMain
import com.google.android.material.navigation.NavigationBarView
import com.hjq.toast.ToastUtils


@Route(path = RouterUrls.ROUTER_URL_MAIN)
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    private var firstTime = 0L
    private var delayTime = 2000L
    private var toolBarViewHeight = 0

    private val tabs = arrayOf(
        R.id.navigation_main,
        R.id.navigation_second,
        R.id.navigation_third,
        R.id.navigation_fourth,
    )

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click = ProxyClick()
        mDatabind.vm = mViewModel
        toolBarViewHeight = QMUIStatusBarHelper.getStatusbarHeight(this)
        //修复切换底部导航栏时Icon未改变
        mDatabind.bottomNavigation.itemIconTintList = null

        //初始化viewpager2
        mDatabind.viewPager.initMain(this, mDatabind.bottomNavigation, false)

        //给底部导航栏菜单项添加点击事件
        mDatabind.bottomNavigation.setOnItemSelectedListener(mItemSelectedListener)

        //拦截BottomNavigation长按事件 防止长按时出现Toast
        mDatabind.bottomNavigation.interceptLongClick(tabs)

        //沉浸状态栏
        QMUIStatusBarHelper.translucent(this)

        //首页自动贴边View点击事件
        mDatabind.slFruitImage.setOnDragViewClickListener(object :
            SlideImageView.OnDrawViewClickListener {
            override fun onDragViewClick() {
                ProxyClick().toFruit()
            }
        })
    }

    override fun createObserver() {
        super.createObserver()
        //跳转到首页Tab
        appViewModel.intentToMainTab.observeInActivity(this) {
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
                    mDatabind.viewPager.currentItem = 0
                    return@OnItemSelectedListener true
                }

                R.id.navigation_second -> {
                    mDatabind.viewPager.currentItem = 1
                    return@OnItemSelectedListener true
                }

                R.id.navigation_third -> {
                    mDatabind.viewPager.currentItem = 2
                    return@OnItemSelectedListener true
                }

                R.id.navigation_fourth -> {
                    mDatabind.viewPager.currentItem = 3
                    return@OnItemSelectedListener true
                }
            }
            return@OnItemSelectedListener false
        }

    inner class ProxyClick {
        fun toFruit() {
            ToastUtils.show("Lemon")
        }

        fun toFruitDel() {
            mViewModel.isShowFruitView.value = false
        }
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