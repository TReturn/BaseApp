package com.example.lib_main.ui.adapter

import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.lib_base.room.entity.NoteEntity
import com.example.lib_main.R

/**
 * @CreateDate: 2023/8/25 0:05
 * @Author: 青柠
 * @Description:
 */
class NoteListAdapter :
    BaseQuickAdapter<NoteEntity, BaseViewHolder>(R.layout.item_note_list) {
    override fun convert(holder: BaseViewHolder, item: NoteEntity) {

        holder.run {
            setText(R.id.tvTitle, item.title)
            setText(R.id.tvContent, item.content)

            setText(R.id.tvTime, TimeUtils.getFriendlyTimeSpanByNow(item.time))
        }

    }
}