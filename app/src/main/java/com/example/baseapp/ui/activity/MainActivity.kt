package com.example.baseapp.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.example.baseapp.R
import com.example.baseapp.databinding.ActivityMainBinding
import com.example.baseapp.viewmodel.MainViewModel
import com.example.lib_base.appViewModel
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.constant.RouterUrls
import com.example.lib_base.ext.interceptLongClick
import com.example.lib_base.utils.qmui.QMUIStatusBarHelper
import com.example.lib_base.widget.SlideImageView
import com.example.lib_main.ext.initMain
import com.google.android.material.navigation.NavigationBarView
import com.hjq.toast.Toaster


@Route(path = RouterUrls.ROUTER_URL_MAIN)
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    private var exitTime = 0L

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel

        //沉浸状态栏
        QMUIStatusBarHelper.translucent(this)

        //返回键监听
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val nav = Navigation.findNavController(this@MainActivity, R.id.hostFragment)
                if (nav.currentDestination != null && nav.currentDestination!!.id != R.id.mainFragment) {
                    //如果当前界面不是主页，那么直接调用返回即可
                    nav.navigateUp()
                } else {
                    //主页按两次返回键退出应用
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        Toaster.show(resources.getString(R.string.main_finish))
                        exitTime = System.currentTimeMillis()
                    } else {
                        finish()
                    }
                }
            }
        })
    }

}