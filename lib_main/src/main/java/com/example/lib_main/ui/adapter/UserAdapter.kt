package com.example.lib_main.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.lib_base.utils.image.GlideUtils
import com.example.lib_main.R
import com.example.lib_main.model.UserModel

/**
 * @CreateDate: 2021/12/24 2:18 下午
 * @Author: 青柠
 * @Description:
 */
class UserAdapter :
    BaseQuickAdapter<UserModel, BaseViewHolder>(R.layout.item_user) {

    override fun convert(holder: BaseViewHolder, item: UserModel) {

        holder.run {
            setText(R.id.tvUserTitle, item.title)
            setText(R.id.tvSubTitle, item.subTitle)
            GlideUtils.loadImage(context, item.imageRes, getView(R.id.ivUserSetting))

            //是否显示分割线
            setVisible(R.id.vLine,holder.layoutPosition != itemCount-1)
        }
    }
}