package com.example.lib_main.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.lib_base.utils.image.GlideUtils
import com.example.lib_main.R
import com.example.lib_main.model.DemoTypeModel

/**
 * @CreateDate : 2023/3/30 17:42
 * @Author : 青柠
 * @Description :
 */
class DemoTypeAdapter :
    BaseQuickAdapter<DemoTypeModel, BaseViewHolder>(R.layout.item_demo_type) {

    override fun convert(holder: BaseViewHolder, item: DemoTypeModel) {
        holder.setText(R.id.tvTitle, item.title)
        GlideUtils.loadImageProtist(context, item.pic, holder.getView(R.id.ivPic))

    }

}