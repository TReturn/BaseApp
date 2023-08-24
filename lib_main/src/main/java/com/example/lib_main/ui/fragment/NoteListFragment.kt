package com.example.lib_main.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.ext.init
import com.example.lib_base.utils.image.BigImageUtils
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentNoteListBinding
import com.example.lib_main.ui.adapter.NoteListAdapter
import com.example.lib_main.viewmodel.NoteListViewModel
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

/**
 * @CreateDate: 2023/8/24 23:35
 * @Author: 青柠
 * @Description:备忘录列表
 */
class NoteListFragment : BaseFragment<NoteListViewModel, FragmentNoteListBinding>() {

    private val noteListAdapter: NoteListAdapter by lazy { NoteListAdapter() }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
        setTranslucent(mDatabind.flTranslucent)

        mDatabind.include.titleBar.title = getString(R.string.main_type_note)
        mDatabind.include.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                nav().navigateUp()
            }
        })

        initAdapter()

    }

    override fun onResume() {
        super.onResume()
        mViewModel.getDataBase()
    }

    private fun initAdapter() {
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mDatabind.rvNoteList.init(layoutManager, noteListAdapter, false)
        noteListAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                nav().navigateAction(R.id.action_note_to_detail, Bundle().apply {
                    putInt("ID", data[position].id)
                    putString("TITLE", data[position].title)
                    putString("CONTENT", data[position].content)
                })
            }
        }
    }

    override fun createObserver() {
        mViewModel.noteListData.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                noteListAdapter.setEmptyView(R.layout.empty_view)
            } else {
                //列表倒序
                noteListAdapter.setList(it.reversed())
            }
        }
    }

    inner class ProxyClick {
        fun toAdd() {
            nav().navigateAction(R.id.action_note_to_detail)
        }
    }

}