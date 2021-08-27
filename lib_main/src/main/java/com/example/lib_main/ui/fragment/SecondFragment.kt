package com.example.lib_main.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.ext.bindViewPager2
import com.example.lib_base.ext.init
import com.example.lib_base.utils.qmui.QMUIStatusBarHelper
import com.example.lib_base.utils.ui.ViewLayoutUtils
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentSecondBinding
import com.example.lib_main.viewmodel.SecondViewModel

/**
 * @CreateDate : 2020/12/31
 * @Author : 青柠
 * @Description :
 */
class SecondFragment : BaseFragment<SecondViewModel, FragmentSecondBinding>() {

    //fragment集合
    private var fragmentList: ArrayList<Fragment> = arrayListOf()

    //标题集合
    private var titleList: ArrayList<String> = arrayListOf()

    override fun layoutId(): Int {
        return R.layout.fragment_second
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        ViewLayoutUtils.setHeight(  mDatabind.flTranslucent, QMUIStatusBarHelper.getStatusbarHeight(activity))
        activity?.let {
            //初始化viewpager2
            mDatabind.vpNews.init(it, fragmentList)
            //初始化magicIndicator
            mDatabind.magicIndicator.bindViewPager2(it,mDatabind.vpNews, titleList, 1)
        }
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.categoryDataState.observe(viewLifecycleOwner,{
            //网络请求到数据再动态添加fragment
            for (i in it.indices){
                titleList.add(it[i].type)
                fragmentList.add(NewsFragment.newInstance(it[i].type))
            }
            mDatabind.magicIndicator.navigator.notifyDataSetChanged()
            mDatabind.vpNews.adapter?.notifyDataSetChanged()
            //预加载
            mDatabind.vpNews.offscreenPageLimit = fragmentList.size - 1
        })
    }

    override fun lazyLoadData() {

    }

    override fun initData() {

    }

}