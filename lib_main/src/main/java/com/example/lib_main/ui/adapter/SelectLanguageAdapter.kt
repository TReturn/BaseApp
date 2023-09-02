package com.example.lib_main.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_main.databinding.ItemSelectLanguageBinding
import com.example.lib_main.model.SelectLanguageModel

/**
 * @CreateDate : 2023/3/30 17:42
 * @Author : 青柠
 * @Description :
 */
class SelectLanguageAdapter :
    BaseQuickAdapter<SelectLanguageModel, SelectLanguageAdapter.VH>() {

    class VH(
        parent: ViewGroup,
        val binding: ItemSelectLanguageBinding = ItemSelectLanguageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int, item: SelectLanguageModel?) {
        holder.binding.run {
            data = item
            executePendingBindings()
        }
    }

}