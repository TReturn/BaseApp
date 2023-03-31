package com.example.lib_main.ui.adapter

import androidx.appcompat.widget.AppCompatImageView
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
            val tvStatus = getView<AppCompatImageView>(R.id.ivStatus)
            setText(R.id.tvTitle, item.title)
            tvStatus.isSelected = item.isSelect
        }

    }

}