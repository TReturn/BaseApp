package com.example.lib_base.magic

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import java.util.ArrayList

/**
 * @CreateDate: 2020/11/10
 * @Author: 青柠
 * @Description: 通用viewPager适配器
 */
class ViewPagerAdapter(
    fm: FragmentManager,
    fragments: MutableList<Fragment>,
    dataList: MutableList<String>
) :
    FragmentPagerAdapter(fm) {
    private val mDataList: MutableList<String> = dataList
    private val fragments: MutableList<Fragment> = ArrayList()
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mDataList[position]
    }

    /**
     *   重写destroyItem方法，防止Fragment切换时被销毁
     */
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //super.destroyItem(container, position, `object`)
    }

    init {
        this.fragments.addAll(fragments)
    }
}