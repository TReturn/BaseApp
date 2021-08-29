package com.example.lib_base.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.ViewDataBinding
import com.example.lib_base.constant.MMKVKeys
import com.example.lib_base.utils.data.MMKVUtils
import com.example.lib_base.utils.qmui.QMUIStatusBarHelper
import com.example.lib_base.utils.ui.UiUtils
import me.hgj.jetpackmvvm.base.activity.BaseVmDbActivity
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate : 2021/7/29
 * @Author : 青柠
 * @Description :
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbActivity<VM, DB>() {

    abstract override fun layoutId(): Int

    abstract override fun initView(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //白天、夜间模式下设置对应状态栏颜色
        when (MMKVUtils.getInt(MMKVKeys.NIGHT_MODE, 1) ){
            AppCompatDelegate.MODE_NIGHT_NO ->{
                //设置状态栏黑色字体图标
                QMUIStatusBarHelper.setStatusBarLightMode(this)
            }
            AppCompatDelegate.MODE_NIGHT_YES ->{
                //设置状态栏白色字体图标
                QMUIStatusBarHelper.setStatusBarDarkMode(this)
            }
        }
    }

    /**
     * 创建liveData观察者
     */
    override fun createObserver() {}

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        //showLoadingExt(message)
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        //dismissLoadingExt()
    }

    /* *//**
     * 在任何情况下本来适配正常的布局突然出现适配失效，适配异常等问题，只要重写 Activity 的 getResources() 方法
     *//*
    override fun getResources(): Resources {
        AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources())
        return super.getResources()
    }*/
}