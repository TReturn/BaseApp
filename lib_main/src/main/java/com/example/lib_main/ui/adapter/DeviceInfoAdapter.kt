package com.example.lib_main.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.lib_base.utils.image.GlideUtils
import com.example.lib_main.R
import com.example.lib_main.model.DemoTypeModel
import com.example.lib_main.model.DeviceInfoModel

/**
 * @CreateDate : 2023/3/30 17:42
 * @Author : 青柠
 * @Description :
 */
class DeviceInfoAdapter :
    BaseQuickAdapter<DeviceInfoModel, BaseViewHolder>(R.layout.item_device_info) {

    override fun convert(holder: BaseViewHolder, item: DeviceInfoModel) {
        holder.run {
            setText(R.id.tvTitle, item.title)
            setText(R.id.tvContent, item.content)
        }
    }

}