package com.example.lib_main.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.constant.RouterUrls
import com.example.lib_base.ext.init
import com.example.lib_base.utils.ui.TextFontUtils
import com.example.lib_main.R
import com.example.lib_main.databinding.ActivityPoetryBinding
import com.example.lib_main.model.PoetryBean
import com.example.lib_main.ui.adapter.PoetryAdapter
import com.example.lib_main.ui.adapter.SelectLanguageAdapter
import com.example.lib_main.viewmodel.PoetryViewModel
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar


/**
 * @CreateDate: 2023/3/31 18:25
 * @Author: 青柠
 * @Description: 诗歌详情页
 */
@Route(path = RouterUrls.ROUTER_URL_POETRY)
class PoetryActivity :
    BaseActivity<PoetryViewModel, ActivityPoetryBinding>() {

    @JvmField
    @Autowired
    var intentData: PoetryBean.Data? = null

    private val poetryAdapter: PoetryAdapter by lazy { PoetryAdapter() }


    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
        ARouter.getInstance().inject(this)

        //mDatabind.include.titleBar.title = getString(R.string.poetry_title)
        mDatabind.include.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                finish()
            }
        })

        TextFontUtils.load(TextFontUtils.getLiuGQTypeFace(), mDatabind.tvTitle, mDatabind.tvAuthor)

        initAdapter()
    }

    override fun initData() {
        intentData?.run {
            mViewModel.poetryTitle.value = origin.title
            mViewModel.poetryAuthor.value = "${origin.author}(${origin.dynasty})"
            mViewModel.poetryContent.value = origin.content.toString()
            poetryAdapter.setList(origin.content)
        }
    }

    private fun initAdapter() {
        mDatabind.rvPoetry.init(GridLayoutManager(this, 1), poetryAdapter, false)
        poetryAdapter.run {
            setOnItemClickListener { _, _, position ->

            }
        }
    }

    inner class ProxyClick {

    }


}