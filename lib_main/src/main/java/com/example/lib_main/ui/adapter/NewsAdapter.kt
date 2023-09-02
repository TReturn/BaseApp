package com.example.lib_main.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemAdapter
import com.example.lib_main.databinding.ItemProjectsNoPicBinding
import com.example.lib_main.databinding.ItemProjectsOnePicBinding
import com.example.lib_main.model.CategoryTypeData

/**
 * @CreateDate : 2021/8/1 1:49
 * @Author : 青柠
 * @Description :
 */
class NewsAdapter :
    BaseMultiItemAdapter<CategoryTypeData>() {

    class NoPicVH(val viewBinding: ItemProjectsNoPicBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    class OnePicVH(val viewBinding: ItemProjectsOnePicBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    init {
        addItemType(
            NO_PIC_TYPE,
            object : OnMultiItemAdapterListener<CategoryTypeData, NoPicVH> {
                override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): NoPicVH {
                    // 创建 viewHolder
                    val viewBinding =
                        ItemProjectsNoPicBinding.inflate(
                            LayoutInflater.from(context),
                            parent,
                            false
                        )
                    return NoPicVH(viewBinding)
                }

                override fun onBind(
                    holder: NoPicVH,
                    position: Int,
                    item: CategoryTypeData?
                ) {
                    holder.viewBinding.run {
                        data = item
                        executePendingBindings()
                    }
                }
            }).addItemType(
            ONE_PIC_TYPE,
            object : OnMultiItemAdapterListener<CategoryTypeData, OnePicVH> {
                override fun onCreate(
                    context: Context,
                    parent: ViewGroup,
                    viewType: Int
                ): OnePicVH {
                    // 创建 viewHolder
                    val viewBinding =
                        ItemProjectsOnePicBinding.inflate(
                            LayoutInflater.from(context),
                            parent,
                            false
                        )
                    return OnePicVH(viewBinding)
                }

                override fun onBind(
                    holder: OnePicVH,
                    position: Int,
                    item: CategoryTypeData?
                ) {
                    if (item == null) return
                    // 绑定 item 数据
                    holder.viewBinding.run {
                        data = item
                        executePendingBindings()
                    }

                }

                override fun isFullSpanItem(itemType: Int): Boolean {
                    // 使用GridLayoutManager时，此类型的 item 是否是满跨度
                    return true
                }

            }).onItemViewType { position, list ->
            // 根据数据，返回对应的 ItemViewType
            list[position].itemType
        }
    }

    companion object {
        private const val NO_PIC_TYPE = 0
        private const val ONE_PIC_TYPE = 1
    }
}