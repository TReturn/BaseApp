package com.example.lib_main.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.ext.bindViewPager2
import com.example.lib_base.ext.init
import com.example.lib_base.utils.qmui.QMUIStatusBarHelper
import com.example.lib_base.utils.ui.LayoutParamsUtils
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentThirdBinding
import com.example.lib_main.viewmodel.ThirdViewModel

/**
 * @CreateDate : 2020/12/31
 * @Author : 青柠
 * @Description :
 */
class ThirdFragment : BaseFragment<ThirdViewModel, FragmentThirdBinding>() {

    //fragment集合
    private var fragmentList: ArrayList<Fragment> = arrayListOf()

    //标题集合
    private var titleList: ArrayList<String> = arrayListOf()

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        LayoutParamsUtils.setHeight(
            mDatabind.flTranslucent,
            QMUIStatusBarHelper.getStatusbarHeight(activity)
        )
        //初始化viewpager2
        mDatabind.vpNews.init(this, fragmentList)
        //初始化magicIndicator
        mDatabind.magicIndicator.bindViewPager2(mDatabind.vpNews, titleList, false)
    }

    override fun initData() {
        mViewModel.getCategoryData()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun createObserver() {
        super.createObserver()
        mViewModel.categoryDataState.observe(viewLifecycleOwner) {
            //网络请求到数据再动态添加fragment
            for (i in it.indices) {
                titleList.add(it[i].name)
                fragmentList.add(ProjectsFragment.newInstance(it[i].id.toString()))
            }
            mDatabind.magicIndicator.navigator.notifyDataSetChanged()
            mDatabind.vpNews.adapter?.notifyDataSetChanged()
            //预加载
            mDatabind.vpNews.offscreenPageLimit = fragmentList.size - 1
        }
    }


}