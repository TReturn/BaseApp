package com.example.lib_base.magic

import android.content.Context
import android.graphics.Color
import androidx.viewpager.widget.ViewPager
import com.example.lib_base.R
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator


/**
 * @CreateDate: 2020/11/10
 * @Author: 青柠
 * @Description:封装MagicIndicatorAdapter
 */
object MagicIndicatorAdapter {
    /**
     * type = 1,均分，适用3个以内Title。
     * type = 2，自适应，可滑动。
     */
    fun init(
        context: Context,
        type: Int,
        magicIndicator: MagicIndicator,
        mDataList: List<String>,
        viewPager: ViewPager
    ) {

        val commonNavigator = CommonNavigator(context)
        commonNavigator.isAdjustMode = type == 2
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val clipPagerTitleView = MyClipPagerTitleView(context)
                clipPagerTitleView.text = mDataList[index]
                clipPagerTitleView.textColor = Color.parseColor("#939393")
                clipPagerTitleView.clipColor = Color.parseColor("#070639")
                clipPagerTitleView.setOnClickListener {
                    viewPager.currentItem = index
                }
                return clipPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                val navigatorHeight: Float =
                    context.resources.getDimension(R.dimen.common_navigator_height)
                val borderWidth: Float = UIUtil.dip2px(context, 1.0).toFloat()
                val lineHeight = navigatorHeight - 2 * borderWidth
                indicator.lineHeight = lineHeight
                indicator.roundRadius = lineHeight / 2
                indicator.yOffset = borderWidth
                indicator.setColors(Color.parseColor("#FFFFFF"))
                return indicator
            }

        }
        magicIndicator.navigator = commonNavigator
        ViewPagerHelper.bind(magicIndicator, viewPager)
    }
}