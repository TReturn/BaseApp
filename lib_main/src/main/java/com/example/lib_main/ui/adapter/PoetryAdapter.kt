package com.example.lib_main.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_base.utils.ui.TextFontUtils
import com.example.lib_main.databinding.ItemPoetryBinding

/**
 * @CreateDate : 2023/3/30 17:42
 * @Author : 青柠
 * @Description :
 */
class PoetryAdapter :
    BaseQuickAdapter<String, PoetryAdapter.VH>() {

    // 自定义ViewHolder类
    class VH(
        parent: ViewGroup,
        val binding: ItemPoetryBinding = ItemPoetryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        // 返回一个 ViewHolder
        return VH(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int, item: String?) {
        if (item == null) return
        // 设置item数据
        holder.binding.run {
            data = item
            executePendingBindings()
            TextFontUtils.load(TextFontUtils.getLiuGQTypeFace(), tvPoetry)
        }
    }

}