package com.example.lib_base.ext

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_base.R
import com.example.lib_base.list.ListDataUiState
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * @CreateDate : 2021/8/4 00:50
 * @Author : 青柠
 * @Description :项目中自定义类的拓展函数
 */


/**
 * 拦截BottomNavigation长按事件 防止长按时出现Toast ---- 追求完美的大屌群友提的bug
 * @receiver BottomNavigationView
 * @param ids IntArray
 */
fun BottomNavigationView.interceptLongClick(vararg ids: Int) {
    val bottomNavigationMenuView: ViewGroup = (this.getChildAt(0) as ViewGroup)
    for (index in ids.indices) {
        bottomNavigationMenuView.getChildAt(index).findViewById<View>(ids[index])
            .setOnLongClickListener {
                true
            }
    }
}

//绑定普通的Recyclerview
fun RecyclerView.init(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true
): RecyclerView {
    layoutManager = layoutManger
    setHasFixedSize(true)
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll
    return this
}

/**
 * 加载带刷新功能的列表数据
 */
fun <T> loadListData(
    data: ListDataUiState<T>,
    baseQuickAdapter: BaseQuickAdapter<T, *>,
    refreshLayout: SmartRefreshLayout
) {
    if (data.isRefresh) {
        refreshLayout.finishRefresh()
    } else {
        refreshLayout.finishLoadMore()
    }

    //成功
    if (data.isSuccess) {
        when {
            //第一页并没有数据 显示空布局界面
            data.isFirstEmpty -> {
                baseQuickAdapter.setList(data.listData)
                baseQuickAdapter.setEmptyView(R.layout.empty_view)
            }
            //是第一页
            data.isRefresh -> {
                baseQuickAdapter.setList(data.listData)
            }
            //不是第一页，增加数据
            else -> {
                baseQuickAdapter.addData(data.listData)
            }
        }
    } else {
        //失败
        if (data.isRefresh) {
            //如果是第一页，则显示错误界面，并提示错误信息
            baseQuickAdapter.setList(data.listData)
            baseQuickAdapter.setEmptyView(R.layout.empty_view)
        }
        baseQuickAdapter.notifyDataSetChanged()
    }
}




