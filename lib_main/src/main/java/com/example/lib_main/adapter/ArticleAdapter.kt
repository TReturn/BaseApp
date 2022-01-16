package com.example.lib_main.adapter

import android.text.TextUtils
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.lib_main.R
import com.example.lib_main.model.ArticleBean

/**
 * @CreateDate: 2022/1/16 18:33
 * @Author: 青柠
 * @Description:
 */
class ArticleAdapter :
    BaseMultiItemQuickAdapter<ArticleBean.Data.Data, BaseViewHolder>() {

    override fun convert(holder: BaseViewHolder, item: ArticleBean.Data.Data) {
        holder.run {
            item.run {
                setText(R.id.tvTitle, item.title)
                setText(R.id.tvTime, item.niceDate)
                val author = if (!TextUtils.isEmpty(item.author)) {
                    item.author
                } else {
                    item.shareUser
                }

                when (itemViewType) {
                    0 -> {
                        if (!TextUtils.isEmpty(item.superChapterName)) {
                            setText(R.id.tvSource, "《${item.superChapterName}》")
                        }
                        setText(R.id.tvAuthor, "——${author}")
                    }
                    1 -> {
                        setText(R.id.tvAuthor, author)
                    }
                    else -> {}
                }
            }
        }
    }

    init {
        addItemType(0, R.layout.item_article_one_layout)
        addItemType(1, R.layout.item_article_two_layout)
    }

}