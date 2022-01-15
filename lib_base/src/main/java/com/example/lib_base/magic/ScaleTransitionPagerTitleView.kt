package com.example.lib_base.magic

import android.content.Context
import net.lucode.hackware.magicindicator.buildins.UIUtil

import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

/**
 * @CreateDate: 2021/8/24
 * @Author: 青柠
 * @Description:重写TAB标题，实现加粗效果
 */
class ScaleTransitionPagerTitleView(context: Context) : ColorTransitionPagerTitleView(context) {

    private var minScale = 0.7f

    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        super.onEnter(index, totalCount, enterPercent, leftToRight)    // 实现颜色渐变
        scaleX = minScale + (1.0f - minScale) * enterPercent
        scaleY = minScale + (1.0f - minScale) * enterPercent
    }

    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        super.onLeave(index, totalCount, leavePercent, leftToRight)    // 实现颜色渐变
        scaleX = 1.0f + (minScale - 1.0f) * leavePercent
        scaleY = 1.0f + (minScale - 1.0f) * leavePercent
    }

    /**
     * 选中字体加粗，字号变大
     * @param index Int
     * @param totalCount Int
     */
    override fun onSelected(index: Int, totalCount: Int) {
        super.onSelected(index, totalCount)
        setTextColor(mSelectedColor)
        paint.isFakeBoldText = true
    }

    /**
     * 未选中状态
     * @param index Int
     * @param totalCount Int
     */
    override fun onDeselected(index: Int, totalCount: Int) {
        super.onDeselected(index, totalCount)
        setTextColor(mNormalColor)
        paint.isFakeBoldText = false
    }
}
