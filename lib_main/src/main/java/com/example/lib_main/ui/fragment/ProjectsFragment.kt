package com.example.lib_main.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lib_base.ext.init
import com.example.lib_base.ext.loadListData
import com.example.lib_base.base.BaseFragment
import com.example.lib_main.R
import com.example.lib_main.ui.adapter.NewsAdapter
import com.example.lib_main.databinding.FragmentNewsBinding
import com.example.lib_main.viewmodel.NewsViewModel
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

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
            loadListData(mActivity, it, newsAdapter, mDatabind.refreshLayout)
        }
    }

    private fun refreshData(isRefresh: Boolean) {
        mViewModel.getCategoryTypeData(isRefresh, projectsType)
    }

    private fun initAdapter() {
        mDatabind.rvNews.init(LinearLayoutManager(context), newsAdapter, false)
        newsAdapter.run {
            setOnItemClickListener { _, _, position ->
                val data = getItem(position) ?: return@setOnItemClickListener
                nav().navigateAction(R.id.action_main_to_web, Bundle().apply {
                    putString("TITLE", data.title)
                    putString("URL", data.link)
                })
            }
        }
    }

    companion object {
        private const val TYPE = "TYPE"
        fun newInstance(type: String): ProjectsFragment {
            return ProjectsFragment().apply {
                arguments = Bundle().apply {
                    putString(TYPE, type)
                }
            }
        }
    }
}