package com.example.lib_main.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_main.databinding.ItemDemoTypeBinding
import com.example.lib_main.model.DemoTypeModel

/**
 * @CreateDate : 2023/3/30 17:42
 * @Author : 青柠
 * @Description :
 */
class DemoTypeAdapter :
    BaseQuickAdapter<DemoTypeModel, DemoTypeAdapter.VH>() {

    // 自定义ViewHolder类
    class VH(
        parent: ViewGroup,
        val binding: ItemDemoTypeBinding = ItemDemoTypeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int, item: DemoTypeModel?) {
        holder.binding.run {
            data = item
            executePendingBindings()
        }
    }

}