package com.example.lib_main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.lib_main.R
import com.example.lib_main.databinding.ItemArticleLayoutBinding
import com.example.lib_main.model.ArticleDetail

/**
 * @CreateDate: 2022/1/16 18:33
 * @Author: 青柠
 * @Description:
 */
class ArticleAdapter :
    BaseQuickAdapter<ArticleDetail, BaseDataBindingHolder<ItemArticleLayoutBinding>>(R.layout.item_article_layout) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemArticleLayoutBinding>,
        item: ArticleDetail
    ) {
        holder.run {
            //绑定dataBinding
            dataBinding?.let {
                it.data = item
                it.executePendingBindings()
            }
        }

    }
}