package com.example.lib_main.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.lib_base.utils.image.GlideUtils
import com.example.lib_main.R
import com.example.lib_main.model.NewsDataBean

/**
 * @CreateDate : 2021/8/1 1:49
 * @Author : 青柠
 * @Description :
 */
class NewsAdapter :
    BaseMultiItemQuickAdapter<NewsDataBean.Result.Data, BaseViewHolder>() {

    override fun convert(holder: BaseViewHolder, item: NewsDataBean.Result.Data) {

        holder.setText(R.id.tvNewsTitle, item.title)
        holder.setText(R.id.tvNewsSource, item.authorName)
        GlideUtils.loadRoundImageTransform(item.thumbnailPicS, holder.getView(R.id.ivNewsPic), 12)
        when (holder.itemViewType) {
            2 -> {
                GlideUtils.loadRoundImageTransform(item.thumbnailPicS02, holder.getView(R.id.ivNewsPic2), 12)
                GlideUtils.loadRoundImageTransform(item.thumbnailPicS03, holder.getView(R.id.ivNewsPic3), 12)
            }
        }
    }

    init {
        addItemType(1, R.layout.item_news_one_pic)
        addItemType(2, R.layout.item_news_three_pic)
    }

}