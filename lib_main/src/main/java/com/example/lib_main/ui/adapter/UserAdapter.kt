package com.example.lib_main.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_main.databinding.ItemUserBinding
import com.example.lib_main.model.UserModel

/**
 * @CreateDate: 2021/12/24 2:18 下午
 * @Author: 青柠
 * @Description:
 */
class UserAdapter :
    BaseQuickAdapter<UserModel, UserAdapter.VH>() {
    class VH(
        parent: ViewGroup,
        val binding: ItemUserBinding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int, item: UserModel?) {
        holder.binding.run {
            data = item
            executePendingBindings()
        }
    }

}