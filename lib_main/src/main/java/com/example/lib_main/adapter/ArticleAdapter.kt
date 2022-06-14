package com.example.lib_main.adapter

import androidx.annotation.DrawableRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.lib_base.utils.image.GlideUtils
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

            //随机加载一些头像
            val list:MutableList<Int> = arrayListOf(R.drawable.ic_fruit_apple,R.drawable.ic_fruit_lemon,
                R.drawable.ic_fruit_lime)
            GlideUtils.loadImage(context,list[(0..2).random()],getView(R.id.ivHead))
        }


    }
}