package com.example.lib_main.ui.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.constant.ApiUrls
import com.example.lib_base.constant.MMKVKeys
import com.example.lib_base.constant.RouterUrls
import com.example.lib_base.dialog.BaseConfirmDialog
import com.example.lib_base.utils.data.MMKVUtils
import com.example.lib_base.utils.ui.KeyboardUtils
import com.example.lib_base.utils.ui.WebUtils
import com.example.lib_main.R
import com.example.lib_main.databinding.ActivitySearchBinding
import com.example.lib_main.viewmodel.SearchViewModel
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar


/**
 * @CreateDate : 2021/8/11 23:35
 * @Author : 青柠
 * @Description : 搜索页面
 */
@Route(path = RouterUrls.ROUTER_URL_SEARCH)
class SearchActivity : BaseActivity<SearchViewModel, ActivitySearchBinding>() {

    //搜索历史记录，填充到LabelView
    private var searchLabel: ArrayList<String> = ArrayList()

    override fun layoutId(): Int {
        return R.layout.activity_search
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click = ProxyClick()
        mDatabind.vm = mViewModel
        setLabelData()

        //标题栏点击事件
        mDatabind.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                finish()
            }
        })

        //键盘搜索键点击事件
        mDatabind.etSearch.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //如果actionId是搜索的id，则进行下一步的操作
                toSearch(mDatabind.etSearch.text.toString())
            }
            false
        }

        //EditText文本改变监听
        mDatabind.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mViewModel.isShowDeleteSearchContent.postValue(
                    mDatabind.etSearch.text.toString().isNotEmpty()
                )
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })

        //标签的点击监听
        mDatabind.labelsView.setOnLabelClickListener { label, data, position ->
            //label是被点击的标签，data是标签所对应的数据，position是标签的位置。
            toSearch(label.text.toString())
            mViewModel.searchContent.value = label.text.toString()
        }
    }

    /**
     * 从缓存中加载数据填充Label
     */
    private fun setLabelData() {
        if (!TextUtils.isEmpty(MMKVUtils.getString(MMKVKeys.SEARCH_HISTORY))) {
            val set: Set<String> = MMKVUtils.getStringSet(MMKVKeys.SEARCH_HISTORY)
            for (s in set) {
                searchLabel.add(s)
            }
            mDatabind.labelsView.setLabels(searchLabel)
        }
    }

    /**
     * 保存搜索记录
     * @param editContent String
     */
    private fun saveSearchContent(editContent: String) {
        searchLabel.add(0, editContent)
        mDatabind.labelsView.setLabels(searchLabel)

        val set: MutableSet<String> = HashSet()
        set.add(editContent)
        if (!TextUtils.isEmpty(MMKVUtils.getString(MMKVKeys.SEARCH_HISTORY))) {
            set.addAll(MMKVUtils.getStringSet(MMKVKeys.SEARCH_HISTORY))
        }
        MMKVUtils.put(MMKVKeys.SEARCH_HISTORY, set)
    }

    /**
     * 清空历史记录
     */
    private fun deleteAllSearchHistory() {
        MMKVUtils.put(MMKVKeys.SEARCH_HISTORY, "")
        searchLabel.clear()
        mDatabind.labelsView.setLabels(searchLabel)
    }

    /**
     * 百度搜索
     * @param editContent String
     */
    private fun toSearch(editContent: String) {
        if (TextUtils.isEmpty(editContent)) {
            return
        }
        saveSearchContent(editContent)

        mViewModel.isShowSearchHistory.value = false
        KeyboardUtils.hideSoftInput(mDatabind.etSearch)
        WebUtils.load(
            this,
            mDatabind.clSearchResult,
            "${ApiUrls.BAIDU_SEARCH_URL} $editContent"
        )
    }


    inner class ProxyClick {
        fun toDeleteContent() {
            mViewModel.searchContent.value = ""
            mViewModel.isShowSearchHistory.value = true
        }

        fun toDeleteAllHistory() {
            BaseConfirmDialog(this@SearchActivity, "", getString(R.string.main_search_delete_all)) {
                deleteAllSearchHistory()
            }.show()
        }
    }
}