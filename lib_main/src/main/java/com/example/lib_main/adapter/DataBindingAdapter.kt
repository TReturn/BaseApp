package com.example.lib_main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.lib_main.R
import com.example.lib_main.databinding.ItemArticleLayoutBinding
import com.example.lib_main.model.ArticleBean

/**
 * @CreateDate: 2022/1/16 18:33
 * @Author: 青柠
 * @Description:
 */
class DataBindingAdapter :
    BaseQuickAdapter<ArticleBean.Data.Data, BaseDataBindingHolder<ItemArticleLayoutBinding>>(R.layout.item_article_layout) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemArticleLayoutBinding>,
        item: ArticleBean.Data.Data
    ) {
        // 获取 Binding
        val binding = holder.dataBinding
        if (binding != null) {
            binding.data = item
            binding.executePendingBindings()
        }
    }
}