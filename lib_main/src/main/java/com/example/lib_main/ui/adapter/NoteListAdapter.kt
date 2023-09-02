package com.example.lib_main.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_base.room.entity.NoteEntity
import com.example.lib_main.databinding.ItemNoteListBinding

/**
 * @CreateDate: 2023/8/25 0:05
 * @Author: 青柠
 * @Description:
 */
class NoteListAdapter :
    BaseQuickAdapter<NoteEntity, NoteListAdapter.VH>() {

    // 自定义ViewHolder类
    class VH(
        parent: ViewGroup,
        val binding: ItemNoteListBinding = ItemNoteListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        // 返回一个 ViewHolder
        return VH(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int, item: NoteEntity?) {
        if (item == null) return
        // 设置item数据
        holder.binding.run {
            tvTitle.text = item.title
            tvContent.text = item.content

            tvTime.text = TimeUtils.getFriendlyTimeSpanByNow(item.time)
        }
    }

}