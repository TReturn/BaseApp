package com.example.lib_main.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat.recreate
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lib_base.appViewModel
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.constant.UserKeys
import com.example.lib_base.ext.init
import com.example.lib_base.utils.data.MMKVUtils
import com.example.lib_base.utils.ui.UiUtils
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentFourthBinding
import com.example.lib_main.manager.DialogListener
import com.example.lib_main.manager.DialogManager
import com.example.lib_main.model.UserModel
import com.example.lib_main.ui.activity.AboutComposeActivity
import com.example.lib_main.ui.adapter.UserAdapter
import com.hjq.language.MultiLanguages
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction
import java.util.*


/**
 * @CreateDate : 2020/12/31
 * @Author : 青柠
 * @Description :
 */
class FourFragment : BaseFragment<BaseViewModel, FragmentFourthBinding>() {

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
        when (MMKVUtils.getInt(UserKeys.NIGHT_MODE, 1)) {
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
                add(UserModel(0, "用户协议", "", R.drawable.ic_user_line))
                add(UserModel(1, "隐私政策", "", R.drawable.ic_user_book))
                add(UserModel(2, "清除缓存", "", R.drawable.ic_user_power))
                add(
                    UserModel(
                        3,
                        getString(R.string.user_switch_language),
                        "",
                        R.drawable.ic_user_language
                    )
                )
                add(UserModel(4, "关于我们", "", R.drawable.ic_user_about))
            }
        )
    }

    private fun initAdapter() {
        mDatabind.rvUser.init(GridLayoutManager(context, 1), userAdapter, false)
        userAdapter.run {
            setOnItemClickListener { _, view, position ->
                when (data[position].id) {
                    0 -> {
                        nav().navigateAction(R.id.action_main_to_web, Bundle().apply {
                            putString("TITLE", data[position].title)
                            putString("URL", "")
                        })
                    }

                    1 -> {
                        nav().navigateAction(R.id.action_main_to_web, Bundle().apply {
                            putString("TITLE", data[position].title)
                            putString("URL", "")
                        })
                    }

                    3 -> {
                        //切换语言
                        DialogManager.showSelectLanguageDialog(mActivity)
                            .setListener(object : DialogListener {
                                override fun onConfirm(type: String) {
                                    setLanguage()
                                }
                            })
                    }

                    4 -> {
                        //关于我们
                        mActivity.startActivity(Intent(mActivity, AboutComposeActivity::class.java))
                    }
                }
            }
        }
    }

    /**
     * 设置语言
     */
    private fun setLanguage() {
        // 是否需要重启
        val restart: Boolean = when (MMKVUtils.getInt(UserKeys.LANGUAGE_TYPE)) {
            0 -> MultiLanguages.clearAppLanguage(mActivity)
            1 -> MultiLanguages.setAppLanguage(mActivity, Locale.CHINA)
            2 -> MultiLanguages.setAppLanguage(mActivity, Locale.ENGLISH)
            else -> false
        }
        if (restart) {
            appViewModel.isRestart.value = true
            activity?.finish()
        }
    }

    inner class ProxyClick {
        fun toSwitch() {
            //日间、夜间切换
            when (MMKVUtils.getInt(UserKeys.NIGHT_MODE, 1)) {
                AppCompatDelegate.MODE_NIGHT_NO -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    MMKVUtils.put(UserKeys.NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_YES)
                    mDatabind.toolbar.menu.getItem(0).icon =
                        UiUtils.getDrawable(R.drawable.ic_dark_themes)
                }

                AppCompatDelegate.MODE_NIGHT_YES -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    MMKVUtils.put(UserKeys.NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO)
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