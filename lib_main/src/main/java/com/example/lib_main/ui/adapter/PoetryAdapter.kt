package com.example.lib_main.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.lib_base.utils.ui.TextFontUtils
import com.example.lib_main.R

/**
 * @CreateDate : 2023/3/30 17:42
 * @Author : 青柠
 * @Description :
 */
class PoetryAdapter :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_poetry) {

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.run {
            setText(R.id.tvPoetry, item)
            TextFontUtils.load(TextFontUtils.getLiuGQTypeFace(), getView<TextView>(R.id.tvPoetry))
        }
    }

}