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
    BaseMultiItemQuickAdapter<GankCategoryTypeBean.Data.Data, BaseViewHolder>() {

    override fun convert(holder: BaseViewHolder, item: GankCategoryTypeBean.Data.Data) {

        holder.setText(R.id.tvNewsTitle, item.title)
        holder.setText(R.id.tvNewsSource, item.author)
        holder.setText(R.id.tvNewsTime, item.niceDate)
        //holder.setText(R.id.tvNewsTime, TimeUtils.getFriendlyTimeSpanByNow(item.niceDate))
        holder.setText(R.id.tvNewsDesc, item.desc)

        when (holder.itemViewType) {
            0 -> {

            }
            1 -> {
                GlideUtils.loadRoundImageTransform(context,item.envelopePic, holder.getView(R.id.ivNewsPic), 12)
            }
        }
    }

    init {
        addItemType(0, R.layout.item_news_no_pic)
        addItemType(1, R.layout.item_news_one_pic)
    }

}