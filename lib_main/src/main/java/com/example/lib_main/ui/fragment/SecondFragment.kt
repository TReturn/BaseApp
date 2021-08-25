package com.example.lib_main.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.lib_base.BaseApplication
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.constant.StaticConstants
import com.example.lib_base.magic.ViewPagerAdapter
import com.example.lib_base.magic.MagicIndicatorAdapter
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentSecondBinding
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate : 2020/12/31
 * @Author : 青柠
 * @Description :
 */
class SecondFragment : BaseFragment<BaseViewModel, FragmentSecondBinding>() {

    override fun layoutId(): Int {
        return R.layout.fragment_second
    }

    override fun initView(savedInstanceState: Bundle?) {
        val fragmentList: MutableList<Fragment> = ArrayList()
        val titleList: MutableList<String> = ArrayList()

        fragmentList.add(NewsFragment.newInstance(StaticConstants.NEWS_TOP))
        fragmentList.add(NewsFragment.newInstance(StaticConstants.NEWS_TECHNOLOGY))
        fragmentList.add(NewsFragment.newInstance(StaticConstants.NEWS_ENTERTAINMENT))
        fragmentList.add(NewsFragment.newInstance(StaticConstants.NEWS_INTERNATIONALITY))

        titleList.add(resources.getString(R.string.main_news_top))
        titleList.add(resources.getString(R.string.main_news_technology))
        titleList.add(resources.getString(R.string.main_news_entertainment))
        titleList.add(resources.getString(R.string.main_news_internationality))

        mDatabind.vpNews.adapter =  ViewPagerAdapter(childFragmentManager, fragmentList, titleList)
        //预加载
        //mDatabind.vpNews.offscreenPageLimit = fragmentList.size - 1
        MagicIndicatorAdapter.init(BaseApplication.context, 1, mDatabind.magicIndicator, titleList, mDatabind.vpNews)

    }

    override fun initData() {

    }

}