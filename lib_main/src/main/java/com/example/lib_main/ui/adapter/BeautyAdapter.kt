package com.example.lib_main.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.lib_base.utils.image.GlideUtils
import com.example.lib_main.R
import com.example.lib_main.model.BeautyBean

/**
 * @CreateDate : 2021/8/1 1:49
 * @Author : 青柠
 * @Description :
 */
class BeautyAdapter :
    BaseQuickAdapter<BeautyBean.Data, BaseViewHolder>(R.layout.item_beauty) {

    override fun convert(holder: BaseViewHolder, item: BeautyBean.Data) {

        holder.setText(R.id.tvTitle, item.title)
        holder.setText(R.id.tvRead, context.getString(R.string.main_people_reading,item.views.toString()))
        GlideUtils.loadRoundImage(context,item.url, holder.getView(R.id.ivPic), 12F)

    }

}