package com.example.lib_main.adapter

import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.lib_base.utils.image.GlideUtils
import com.example.lib_main.R
import com.example.lib_main.model.GankCategoryTypeBean

/**
 * @CreateDate : 2021/8/1 1:49
 * @Author : 青柠
 * @Description :
 */
class NewsAdapter :
    BaseMultiItemQuickAdapter<GankCategoryTypeBean.Data, BaseViewHolder>() {

    override fun convert(holder: BaseViewHolder, item: GankCategoryTypeBean.Data) {

        holder.setText(R.id.tvNewsTitle, item.title)
        holder.setText(R.id.tvNewsSource, item.author)
        holder.setText(R.id.tvNewsTime, TimeUtils.getFriendlyTimeSpanByNow(item.publishedAt))
        holder.setText(R.id.tvNewsDesc, item.desc)

        when (holder.itemViewType) {
            0 -> {

            }
            1 -> {
                GlideUtils.loadRoundImageTransform(item.images[0], holder.getView(R.id.ivNewsPic), 12)
            }
            2 -> {
                GlideUtils.loadRoundImageTransform(item.images[0], holder.getView(R.id.ivNewsPic), 12)
                GlideUtils.loadRoundImageTransform(item.images[1], holder.getView(R.id.ivNewsPic2), 12)
                GlideUtils.loadRoundImageTransform(item.images[2], holder.getView(R.id.ivNewsPic3), 12)
            }
        }
    }

    init {
        addItemType(0, R.layout.item_news_no_pic)
        addItemType(1, R.layout.item_news_one_pic)
        addItemType(2, R.layout.item_news_three_pic)
    }

}