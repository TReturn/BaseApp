package com.example.lib_main.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lib_base.ext.init
import com.example.lib_base.ext.loadListData
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.router.RouterUtils
import com.example.lib_main.R
import com.example.lib_main.adapter.NewsAdapter
import com.example.lib_main.databinding.FragmentNewsBinding
import com.example.lib_main.viewmodel.NewsViewModel

/**
 * @CreateDate : 2021/8/2 21:33
 * @Author : 青柠
 * @Description :
 */
class NewsFragment : BaseFragment<NewsViewModel, FragmentNewsBinding>() {

    private val newsAdapter: NewsAdapter by lazy { NewsAdapter() }

    //新闻类型
    private lateinit var newsType: String

    override fun layoutId(): Int {
        return R.layout.fragment_news
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewmodel = mViewModel
        arguments?.run {
            newsType = getString(TYPE, "top")
        }
        initAdapter()

        mDatabind.refreshLayout.autoRefresh()
        mDatabind.refreshLayout.setOnRefreshListener { refreshData(true) }
        mDatabind.refreshLayout.setOnLoadMoreListener { refreshData(false) }
    }

    override fun createObserver() {
        mViewModel.newsDataState.observe(viewLifecycleOwner, {
            loadListData(it,newsAdapter,mDatabind.refreshLayout)
        })
    }

    private fun refreshData(isRefresh:Boolean){
        mViewModel.getNewsData(isRefresh,newsType,10)
    }

    private fun initAdapter() {
        mDatabind.rvNews.init(LinearLayoutManager(context), newsAdapter, false)
        newsAdapter.run {
            setOnItemClickListener { _, _, position ->
                RouterUtils.web(data[position].url, data[position].title)
            }
        }
    }

    companion object {
        private const val TYPE = "TYPE"
        fun newInstance(type: String): NewsFragment {
            val bundle = Bundle()
            val fragment = NewsFragment()
            bundle.putString(TYPE, type)
            fragment.arguments = bundle
            return fragment
        }
    }
}