package com.example.lib_main.ui.dialog

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lib_base.constant.UserKeys
import com.example.lib_base.ext.init
import com.example.lib_base.utils.data.MMKVUtils
import com.example.lib_main.R
import com.example.lib_main.model.SelectLanguageModel
import com.example.lib_main.ui.adapter.SelectLanguageAdapter
import com.hjq.shape.view.ShapeTextView
import com.lxj.xpopup.core.CenterPopupView

/**
 * @CreateDate: 2023/3/31 10:59
 * @Author: 青柠
 * @Description:
 */
@SuppressLint("ViewConstructor")
class SelectLanguageDialog(context: Context, private val confirmCallback: () -> Unit) :
    CenterPopupView(context) {

    private val languageAdapter: SelectLanguageAdapter by lazy { SelectLanguageAdapter() }

    //当前已设置的语言
    private val nowLanguage = MMKVUtils.getInt(UserKeys.LANGUAGE_TYPE)

    //语言类型：0跟随系统，1中文，2英文
    private var selectType = nowLanguage

    // 返回自定义弹窗的布局
    override fun getImplLayoutId(): Int {
        return R.layout.dialog_select_language
    }

    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    override fun onCreate() {
        super.onCreate()

        findViewById<ShapeTextView>(R.id.tvCancel).setOnClickListener {
            dismiss()
        }
        findViewById<ShapeTextView>(R.id.tvConfirm).setOnClickListener {
            if (nowLanguage != selectType) {
                MMKVUtils.put(UserKeys.LANGUAGE_TYPE, selectType)
                confirmCallback.invoke()
            }
            dismiss()
        }

        initAdapter(findViewById(R.id.rvLanguage))
        initData()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initAdapter(rv: RecyclerView) {
        rv.init(GridLayoutManager(context, 1), languageAdapter, false)
        languageAdapter.run {
            setOnItemClickListener { _, _, position ->
                val data = getItem(position) ?: return@setOnItemClickListener
                for (i in items) {
                    i.isSelect = false
                }
                data.isSelect = true
                selectType = position
                notifyDataSetChanged()
            }
        }
    }

    private fun initData() {
        val languageList: MutableList<SelectLanguageModel> = arrayListOf()
        languageAdapter.submitList(
            languageList.apply {
                add(SelectLanguageModel("跟随系统", nowLanguage == 0))
                add(SelectLanguageModel("中文", nowLanguage == 1))
                add(SelectLanguageModel("英文", nowLanguage == 2))
            }
        )
    }

}