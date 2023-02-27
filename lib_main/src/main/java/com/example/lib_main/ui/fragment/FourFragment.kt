package com.example.lib_main.ui.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat.recreate
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.constant.MMKVKeys
import com.example.lib_base.constant.RouterUrls
import com.example.lib_base.ext.init
import com.example.lib_base.router.RouterUtils
import com.example.lib_base.utils.data.MMKVUtils
import com.example.lib_base.utils.ui.UiUtils
import com.example.lib_main.R
import com.example.lib_main.adapter.UserAdapter
import com.example.lib_main.databinding.FragmentFourBinding
import com.example.lib_main.model.UserModel
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel


/**
 * @CreateDate : 2020/12/31
 * @Author : 青柠
 * @Description :
 */
class FourFragment : BaseFragment<BaseViewModel, FragmentFourBinding>() {

    private val userAdapter: UserAdapter by lazy { UserAdapter() }

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
        when (MMKVUtils.getInt(MMKVKeys.NIGHT_MODE, 1)) {
            AppCompatDelegate.MODE_NIGHT_NO -> {
                mDatabind.toolbar.menu.getItem(0).icon =
                    UiUtils.getDrawable(R.drawable.ic_dark_themes)
            }
            AppCompatDelegate.MODE_NIGHT_YES -> {
                mDatabind.toolbar.menu.getItem(0).icon =
                    UiUtils.getDrawable(R.drawable.ic_day_themes)
            }
        }

        initAdapter()

    }

    override fun initData() {
        val userList: MutableList<UserModel> = arrayListOf()
        userAdapter.setList(
            userList.apply {
                add(UserModel(0, "用户协议","",R.drawable.ic_user_line))
                add(UserModel(1, "隐私政策","",R.drawable.ic_user_book))
                add(UserModel(2, "清除缓存","",R.drawable.ic_user_power))
                add(UserModel(3, "关于我们","",R.drawable.ic_user_about))
            }
        )
    }

    private fun initAdapter() {
        mDatabind.rvUser.init(GridLayoutManager(context, 1), userAdapter, false)
        userAdapter.run {
            setOnItemClickListener { _, _, position ->
                when (data[position].id) {
                    3 -> RouterUtils.intent(RouterUrls.ROUTER_URL_ABOUT_COMPOSE)
                }
            }
        }
    }

    inner class ProxyClick {
        fun toSwitch() {
            //日间、夜间切换
            when (MMKVUtils.getInt(MMKVKeys.NIGHT_MODE, 1)) {
                AppCompatDelegate.MODE_NIGHT_NO -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    MMKVUtils.put(MMKVKeys.NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_YES)
                    mDatabind.toolbar.menu.getItem(0).icon =
                        UiUtils.getDrawable(R.drawable.ic_dark_themes)
                }
                AppCompatDelegate.MODE_NIGHT_YES -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    MMKVUtils.put(MMKVKeys.NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO)
                    mDatabind.toolbar.menu.getItem(0).icon =
                        UiUtils.getDrawable(R.drawable.ic_day_themes)
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