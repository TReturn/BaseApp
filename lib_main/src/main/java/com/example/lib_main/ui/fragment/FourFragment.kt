package com.example.lib_main.ui.fragment

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.utils.qmui.QMUIStatusBarHelper
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentFourBinding
import com.hjq.toast.ToastUtils
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel


/**
 * @CreateDate : 2020/12/31
 * @Author : 青柠
 * @Description :
 */
class FourFragment : BaseFragment<BaseViewModel, FragmentFourBinding>() {

    override fun layoutId(): Int {
        return R.layout.fragment_four
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.toolbar.inflateMenu(R.menu.setting_menu)

        //设置渐变监听
        mDatabind.collapseLayout.setOnScrimsListener { layout, shown ->

        }

        //标题栏菜单监听
        mDatabind.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.ivDarkSwitch -> ProxyClick().toSwitch()
                R.id.ivSetting -> ProxyClick().toSetting()
            }
            true
        }

    }

    override fun initData() {

    }

    inner class ProxyClick {
        fun toSwitch() {

        }

        fun toSetting() {

        }
    }

}