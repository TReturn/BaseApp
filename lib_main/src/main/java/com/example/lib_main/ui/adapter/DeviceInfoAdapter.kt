package com.example.lib_main.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_main.databinding.ItemDeviceInfoBinding
import com.example.lib_main.model.DeviceInfoModel

/**
 * @CreateDate : 2023/3/30 17:42
 * @Author : 青柠
 * @Description :
 */
class DeviceInfoAdapter :
    BaseQuickAdapter<DeviceInfoModel, DeviceInfoAdapter.VH>() {
    class VH(
        parent: ViewGroup,
        val binding: ItemDeviceInfoBinding = ItemDeviceInfoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int, item: DeviceInfoModel?) {
        holder.binding.run {
            data = item
            executePendingBindings()
        }
    }

}