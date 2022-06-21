package com.example.lib_main.ui.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.constant.RouterUrls
import com.example.lib_base.ext.init
import com.example.lib_base.ext.loadListData
import com.example.lib_base.utils.image.BigImageUtils
import com.example.lib_base.utils.ui.UiUtils
import com.example.lib_main.R
import com.example.lib_main.adapter.BeautyAdapter
import com.example.lib_main.adapter.BeautyDetailAdapter
import com.example.lib_main.databinding.ActivityBeautyBinding
import com.example.lib_main.viewmodel.MainViewModel
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar

/**
 * @CreateDate : 2021/8/19 00:03
 * @Author : 青柠
 * @Description : 妹子瀑布流页面
 */
@Route(path = RouterUrls.ROUTER_URL_BEAUTY)
class BeautyActivity : BaseActivity<MainViewModel, ActivityBeautyBinding>() {

    private val beautyAdapter: BeautyDetailAdapter by lazy { BeautyDetailAdapter() }
    private var beautyImageList = arrayListOf<String>()

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        initAdapter()

        mDatabind.include.titleBar.title = UiUtils.getString(R.string.main_article_title)
        mDatabind.include.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                finish()
            }
        })

        mDatabind.refreshLayout.autoRefresh()
        mDatabind.refreshLayout.setOnRefreshListener {
            beautyImageList.clear()
            //mViewModel.getBeautyData(true)
        }
        //mDatabind.refreshLayout.setOnLoadMoreListener { mViewModel.getBeautyData(false) }
    }

    override fun createObserver() {
        //妹子数据监听
//        mViewModel.beautyDataState.observe(this, {
//            loadListData(it, beautyAdapter, mDatabind.refreshLayout)
//            for (i in it.listData.indices) {
//                beautyImageList.add(it.listData[i].images[0])
//            }
//        })
    }

    private fun initAdapter() {
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mDatabind.rvBeauty.init(layoutManager, beautyAdapter, false)
        beautyAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                BigImageUtils.show(this@BeautyActivity, beautyImageList, position)
            }
        }
    }
}