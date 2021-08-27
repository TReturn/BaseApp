package com.example.lib_base.ext

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_base.R
import com.example.lib_base.list.ListDataUiState
import com.example.lib_base.magic.ScaleTransitionPagerTitleView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import me.hgj.jetpackmvvm.base.appContext
import me.hgj.jetpackmvvm.ext.util.toHtml
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * @CreateDate : 2021/8/4 00:50
 * @Author : 青柠
 * @Description :项目中自定义类的拓展函数
 */



/**
 * MagicIndicator框架绑定ViewPager2
 * @receiver MagicIndicator
 * @param viewPager ViewPager2
 * @param mStringList List<String>
 * @param type type = 1,均分，适用3个以内Title。 type = 2，自适应，可滑动。
 * @param action Function1<[@kotlin.ParameterName] Int, Unit>
 */
fun MagicIndicator.bindViewPager2(
    context:FragmentActivity,
    viewPager: ViewPager2,
    mStringList: List<String> = arrayListOf(),
    type: Int,
    action: (index: Int) -> Unit = {}) {
    val commonNavigator = CommonNavigator(context)
    commonNavigator.isAdjustMode = type == 2
    commonNavigator.isReselectWhenLayout = false
    commonNavigator.adapter = object : CommonNavigatorAdapter() {

        override fun getCount(): Int {
            return  mStringList.size
        }
        override fun getTitleView(context: Context, index: Int): IPagerTitleView {
            return ScaleTransitionPagerTitleView(appContext).apply {
                //设置文本
                text = mStringList[index].toHtml()
                //字体大小
                textSize = 18f
                //未选中颜色
                normalColor = Color.parseColor("#000000")
                //选中颜色
                selectedColor = Color.parseColor("#070639")
                //点击事件
                setOnClickListener {
                    viewPager.currentItem = index
                    action.invoke(index)
                }
            }
        }
        override fun getIndicator(context: Context): IPagerIndicator {
            return LinePagerIndicator(context).apply {
                mode = LinePagerIndicator.MODE_EXACTLY
                //线条的宽高度
                lineHeight = UIUtil.dip2px(appContext, 3.0).toFloat()
                lineWidth = UIUtil.dip2px(appContext, 30.0).toFloat()
                //线条的圆角
                roundRadius = UIUtil.dip2px(appContext, 6.0).toFloat()
                startInterpolator = AccelerateInterpolator()
                endInterpolator = DecelerateInterpolator(2.0f)
                //线条的颜色
                setColors(Color.parseColor("#FF9325"))
            }
        }
    }
    this.navigator = commonNavigator

    viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            this@bindViewPager2.onPageSelected(position)
            action.invoke(position)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            this@bindViewPager2.onPageScrolled(position, positionOffset, positionOffsetPixels)

        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            this@bindViewPager2.onPageScrollStateChanged(state)
        }
    })
}

/**
 * ViewPager2初始化
 * @receiver ViewPager2
 * @param fragments ArrayList<Fragment>
 * @param isUserInputEnabled Boolean
 * @return ViewPager2
 */
fun ViewPager2.init(
    context: FragmentActivity,
    fragments: ArrayList<Fragment>,
    isUserInputEnabled: Boolean = true
): ViewPager2 {
    //是否可滑动
    this.isUserInputEnabled = isUserInputEnabled
    //设置适配器
    adapter = object : FragmentStateAdapter(context) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
    return this
}

/**
 * 拦截BottomNavigation长按事件 防止长按时出现Toast
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




