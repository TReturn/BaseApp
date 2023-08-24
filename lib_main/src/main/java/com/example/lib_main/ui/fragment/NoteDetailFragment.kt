package com.example.lib_main.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.dialog.BaseConfirmDialog
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentNoteDetailBinding
import com.example.lib_main.viewmodel.NoteDetailViewModel
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.hjq.toast.Toaster
import me.hgj.jetpackmvvm.ext.nav

/**
 * @CreateDate: 2023/8/24 23:35
 * @Author: 青柠
 * @Description:备忘录详情
 */
class NoteDetailFragment : BaseFragment<NoteDetailViewModel, FragmentNoteDetailBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
        setTranslucent(mDatabind.flTranslucent)

        arguments?.run {
            val noteID = getInt("ID", -1)
            val title = getString("TITLE", "")
            val content = getString("CONTENT", "")

            mViewModel.title.value = title
            mViewModel.content.value = content
            mViewModel.noteID.value = noteID
            mViewModel.isShowDelete.value = true

        }

        mDatabind.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                nav().navigateUp()
            }
        })

        mDatabind.etTitle.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mViewModel.title.value = mDatabind.etTitle.text.toString()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })

        mDatabind.etContent.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mViewModel.content.value = mDatabind.etContent.text.toString()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })

    }


    inner class ProxyClick {
        fun toSave() {
            if (mViewModel.noteID.value == -1) {
                //插入数据库
                mViewModel.saveDataBase()
            } else {
                //更新数据库
                mViewModel.updateDataBase()
            }

            nav().navigateUp()
        }

        fun toDelete() {
            BaseConfirmDialog(mActivity, "", getString(R.string.note_delete_tips)) {
                mViewModel.deleteDataBase()
                nav().navigateUp()
            }.show()
        }
    }

}