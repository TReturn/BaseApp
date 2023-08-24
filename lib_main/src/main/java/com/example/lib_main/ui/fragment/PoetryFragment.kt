package com.example.lib_main.ui.fragment

import android.os.Build
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.ext.init
import com.example.lib_base.utils.ui.TextFontUtils
import com.example.lib_main.databinding.FragmentPoetryBinding
import com.example.lib_main.model.PoetryBean
import com.example.lib_main.ui.adapter.PoetryAdapter
import com.example.lib_main.viewmodel.PoetryViewModel
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import me.hgj.jetpackmvvm.ext.nav

/**
 * @CreateDate: 2023/8/24 19:03
 * @Author: 青柠
 * @Description: 诗歌详情页
 */
@Suppress("DEPRECATION")
class PoetryFragment : BaseFragment<PoetryViewModel, FragmentPoetryBinding>() {

    private var intentData: PoetryBean.Data? = null

    private val poetryAdapter: PoetryAdapter by lazy { PoetryAdapter() }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
        setTranslucent(mDatabind.flTranslucent)

        mDatabind.include.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                nav().navigateUp()
            }
        })

        TextFontUtils.load(TextFontUtils.getLiuGQTypeFace(), mDatabind.tvTitle, mDatabind.tvAuthor)

        initAdapter()

        arguments?.run {
            var data: PoetryBean.Data? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                //Android13的序列化获取方式
                data = getSerializable("DATA", PoetryBean.Data::class.java)
            } else {
                getSerializable("DATA") as PoetryBean.Data
            }
            data?.run {
                mViewModel.poetryTitle.value = origin.title
                mViewModel.poetryAuthor.value = "${origin.author}(${origin.dynasty})"
                mViewModel.poetryContent.value = origin.content.toString()
                poetryAdapter.setList(origin.content)
            }
        }
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
        mDatabind.rvPoetry.init(GridLayoutManager(mActivity, 1), poetryAdapter, false)
        poetryAdapter.run {
            setOnItemClickListener { _, _, position ->

            }
        }
    }

    inner class ProxyClick {

    }

}