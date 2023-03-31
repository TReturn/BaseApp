package com.example.lib_main.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.lib_main.R
import com.example.lib_main.model.SelectLanguageModel

/**
 * @CreateDate : 2023/3/30 17:42
 * @Author : 青柠
 * @Description :
 */
class SelectLanguageAdapter :
    BaseQuickAdapter<SelectLanguageModel, BaseViewHolder>(R.layout.item_select_language) {

    override fun convert(holder: BaseViewHolder, item: SelectLanguageModel) {
        holder.run {
            val tvStatus = getView<TextView>(R.id.tvStatus)
            setText(R.id.tvTitle, item.title)
            tvStatus.isSelected = item.isSelect
        }

    }

}