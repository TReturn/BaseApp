package com.example.lib_main.ui.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat.recreate
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.constant.MMKVKeys
import com.example.lib_base.utils.data.MMKVUtils
import com.example.lib_base.utils.ui.UiUtils
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentFourBinding
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

        //日间、夜间按钮图标
        when (MMKVUtils.getInt(MMKVKeys.NIGHT_MODE, 1) ){
            AppCompatDelegate.MODE_NIGHT_NO ->{
                mDatabind.toolbar.menu.getItem(0).icon = UiUtils.getDrawable(R.drawable.ic_dark_themes)
            }
            AppCompatDelegate.MODE_NIGHT_YES ->{
                mDatabind.toolbar.menu.getItem(0).icon = UiUtils.getDrawable(R.drawable.ic_day_themes)
            }
        }

    }

    override fun initData() {

    }

    inner class ProxyClick {
        fun toSwitch() {
            //日间、夜间切换
            when (MMKVUtils.getInt(MMKVKeys.NIGHT_MODE, 1) ){
                AppCompatDelegate.MODE_NIGHT_NO ->{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    MMKVUtils.set(MMKVKeys.NIGHT_MODE,AppCompatDelegate.MODE_NIGHT_YES)
                    mDatabind.toolbar.menu.getItem(0).icon = UiUtils.getDrawable(R.drawable.ic_dark_themes)
                }
                AppCompatDelegate.MODE_NIGHT_YES ->{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    MMKVUtils.set(MMKVKeys.NIGHT_MODE,AppCompatDelegate.MODE_NIGHT_NO)
                    mDatabind.toolbar.menu.getItem(0).icon = UiUtils.getDrawable(R.drawable.ic_day_themes)
                }
            }

            activity?.let {
                // 淡出淡入动画效果
                //it.getWindow().setWindowAnimations(R.style.OutInAnimation)
                recreate(it)
            }
        }

        fun toSetting() {

        }
    }

}