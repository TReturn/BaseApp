package com.example.lib_base.widget

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.example.lib_base.utils.qmui.QMUIDisplayHelper
import kotlin.concurrent.thread
import kotlin.math.abs


/**
 * @CreateDate : 2021/8/26 2:17
 * @Author : 青柠
 * @Description : 根据手指拖动的当前位置，自动贴边的View
 */

@SuppressLint("AppCompatCustomView")
class SlideImageView(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs), View.OnTouchListener {
    private val screenWidth: Int
    private val screenHeight: Int
    private var lastX = 0
    private var lastY = 0
    private var maxLeft = 0
    private var maxTop = 0
    private var layoutParams: MarginLayoutParams? = null
    private var startX = 0
    private var endX = 0
    private var isMoved = false

    //整个View点击事件监听
    private var mLister: OnDrawViewClickListener? = null

    //四边距
    private val allMargin = 16

    interface OnDrawViewClickListener {
        fun onDragViewClick()
    }

    fun setOnDragViewClickListener(listener: OnDrawViewClickListener?) {
        mLister = listener
    }


    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.rawX.toInt()
                lastY = event.rawY.toInt()
                startX = lastX
            }
            MotionEvent.ACTION_MOVE -> {
                isMoved = true
                val dx = event.rawX.toInt() - lastX
                val dy = event.rawY.toInt() - lastY
                maxLeft = v.left + dx
                maxTop = v.top + dy
                var right: Int = v.right + dx
                var bottom: Int = v.bottom + dy
                // 设置不能出界
                if (maxLeft < allMargin) {
                    maxLeft = allMargin
                    right = maxLeft + v.width
                }
                if (right > screenWidth - allMargin) {
                    right = screenWidth - allMargin
                    maxLeft = right - v.width
                }
                if (maxTop < allMargin) {
                    maxTop = allMargin
                    bottom = maxTop + v.height
                }
                if (bottom > screenHeight - allMargin) {
                    bottom = screenHeight - allMargin
                    maxTop = bottom - v.height
                }
                v.layout(maxLeft, maxTop, right, bottom)
                lastX = event.rawX.toInt()
                lastY = event.rawY.toInt()
            }
            MotionEvent.ACTION_UP -> {
                //只有滑动改变上边距时，抬起才进行设置
                if (isMoved) {
                    layoutParams = getLayoutParams() as MarginLayoutParams?
                    layoutParams?.let {
                        it.topMargin = maxTop
                        setLayoutParams(it)
                    }

                }
                endX = event.rawX.toInt()
                //滑动距离比较小，当作点击事件处理
                if (abs(startX - endX) < 6) {
                    return false
                }
                if (maxLeft + v.width / 2 < screenWidth / 2) {
                    startScroll(maxLeft, screenWidth / 2, true)
                } else {
                    startScroll(maxLeft, 0, false)
                }
            }
        }
        return true
    }

    //在此处理点击事件
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mLister?.onDragViewClick()
        return super.onTouchEvent(event)
    }

    private fun startScroll(start: Int, end: Int, isLeft: Boolean) {
        val valueAnimator = ValueAnimator.ofFloat(start.toFloat(), end.toFloat()).setDuration(500)
        valueAnimator.addUpdateListener { animation ->
            layoutParams?.let {
                if (isLeft) {
                    it.leftMargin = (start * (1 - animation.animatedFraction)).toInt() + allMargin
                } else {
                    it.leftMargin =
                        (start + (screenWidth - start - width) * animation.animatedFraction).toInt() - allMargin
                }
                setLayoutParams(it)
            }
        }
        valueAnimator.start()
    }


    init {
        //通过QMUI工具类获取屏幕宽高
        screenWidth = QMUIDisplayHelper.getScreenWidth(context)
        screenHeight = QMUIDisplayHelper.getScreenHeight(context) -
                QMUIDisplayHelper.getStatusBarHeight(context) -
                QMUIDisplayHelper.dp2px(context, 54)

        setOnTouchListener(this)

        post {
            //初始化把View放到右下角
            layoutParams = getLayoutParams() as MarginLayoutParams
            layoutParams?.let {
                it.topMargin = screenHeight - height - allMargin
                it.leftMargin = screenWidth - width - allMargin
                setLayoutParams(it)
            }
        }
    }
}