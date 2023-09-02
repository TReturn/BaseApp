package com.example.lib_main.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_main.databinding.ItemArticleLayoutBinding
import com.example.lib_main.model.ArticleDetail

/**
 * @CreateDate: 2022/1/16 18:33
 * @Author: 青柠
 * @Description:
 */
class ArticleAdapter :
    BaseQuickAdapter<ArticleDetail, ArticleAdapter.VH>() {
    class VH(
        parent: ViewGroup,
        val binding: ItemArticleLayoutBinding = ItemArticleLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int, item: ArticleDetail?) {
        holder.binding.run {
            data = item
            executePendingBindings()
        }
    }

}