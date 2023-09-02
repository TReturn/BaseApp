package com.example.lib_main.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_base.utils.image.GlideUtils
import com.example.lib_main.R
import com.example.lib_main.databinding.ItemUserBinding
import com.example.lib_main.model.UserModel

/**
 * @CreateDate: 2021/12/24 2:18 下午
 * @Author: 青柠
 * @Description:
 */
class UserAdapter :
    BaseQuickAdapter<UserModel, UserAdapter.VH>() {

    // 自定义ViewHolder类
    class VH(
        parent: ViewGroup,
        val binding: ItemUserBinding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        // 返回一个 ViewHolder
        return VH(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int, item: UserModel?) {
        if (item == null) return
        // 设置item数据
        holder.binding.run {
            tvUserTitle.text =  item.title
            tvSubTitle.text =  item.subTitle
            GlideUtils.loadImage(context, item.imageRes, ivUserSetting)

            //是否显示分割线
            vLine.visibility = if (holder.layoutPosition != itemCount-1) View.VISIBLE else View.INVISIBLE
        }
    }

}