package com.example.lib_main.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lib_base.ext.init
import com.example.lib_base.ext.loadListData
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.router.RouterUtils
import com.example.lib_main.R
import com.example.lib_main.ui.adapter.NewsAdapter
import com.example.lib_main.databinding.FragmentNewsBinding
import com.example.lib_main.viewmodel.NewsViewModel

/**
 * @CreateDate : 2021/8/2 21:33
 * @Author : 青柠
 * @Description :
 */
class ProjectsFragment : BaseFragment<NewsViewModel, FragmentNewsBinding>() {

    private val newsAdapter: NewsAdapter by lazy { NewsAdapter() }

    //项目类型
    private lateinit var projectsType: String


    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        arguments?.run {
            projectsType = getString(TYPE, "294")
        }
        initAdapter()

        mDatabind.refreshLayout.autoRefresh()
        mDatabind.refreshLayout.setOnRefreshListener { refreshData(true) }
        mDatabind.refreshLayout.setOnLoadMoreListener { refreshData(false) }
    }

    override fun createObserver() {
        mViewModel.categoryTypeDataState.observe(viewLifecycleOwner) {
            loadListData(it, newsAdapter, mDatabind.refreshLayout)
        }
    }

    private fun refreshData(isRefresh: Boolean) {
        mViewModel.getCategoryTypeData(isRefresh, projectsType)
    }

    private fun initAdapter() {
        mDatabind.rvNews.init(LinearLayoutManager(context), newsAdapter, false)
        newsAdapter.run {
            setOnItemClickListener { _, _, position ->
                RouterUtils.web(data[position].link, data[position].title)
            }
        }
    }

    companion object {
        private const val TYPE = "TYPE"
        fun newInstance(type: String): ProjectsFragment {
            val bundle = Bundle()
            val fragment = ProjectsFragment()
            bundle.putString(TYPE, type)
            fragment.arguments = bundle
            return fragment
        }
    }
}