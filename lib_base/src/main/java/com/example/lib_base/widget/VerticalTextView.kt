package com.example.lib_base.widget

import android.annotation.SuppressLint
import android.content.Context
import com.example.lib_base.utils.ui.TextFontUtils.getLiuGQTypeFace
import kotlin.jvm.JvmOverloads
import android.view.MotionEvent
import com.example.lib_base.BaseApplication
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.lib_base.R

/**
 * @CreateDate: 2021/12/28 6:33 下午
 * @Author: 青柠
 * @Description:
 */
class VerticalTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    val TAG = "-------MyTextView"
    private var mText: String?
    private var mTextSize = 16
    private var mTextColor = Color.BLACK
    private val mOrientation: Int
    private val HORIZONTAL = 0
    private val VERTICAL = 1
    private var textLength = 0

    //画笔
    private val mPaint: Paint
    fun setVerticalText(text: String?) {
        mText = text
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //获取宽高的模式
        val widgetMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        //获取宽高的大小
        var widgetSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.AT_MOST -> {
                //计算的宽度 与 字体的长度有关  与字体的大小  用画笔来测量
                val bounds = Rect()
                //获取文本的Rect
                mText?.length?.let { mPaint.getTextBounds(mText, 0, it, bounds) }
                //垂直
                if (mOrientation == VERTICAL) {
                    mText?.let {
                        val strings = it.split("\n").toTypedArray()
                        for (s in strings) {
                            textLength = s.length
                            if (textLength < s.length) {
                                textLength = s.length
                            }
                        }
                        heightSize = bounds.height() * textLength + paddingTop + paddingBottom
                        widgetSize = mTextSize * strings.size + paddingLeft + paddingRight
                    }

                } else {
                    heightSize = bounds.height() + paddingTop + paddingBottom
                    widgetSize = bounds.width() + paddingLeft + paddingRight
                }
            }
            MeasureSpec.EXACTLY -> {}
            MeasureSpec.UNSPECIFIED -> {}
        }
        //设置控件的宽高
        setMeasuredDimension(widgetSize, heightSize)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /**
         * text  文本
         * x  开始的位置
         * y  基线
         * Paint 画笔
         */
        //dy代表的是：高度的一半到baseLine的距离
        //获取文字排版信息
        val fontMetricsInt = mPaint.fontMetricsInt
        val dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom
        //垂直
        if (mOrientation == VERTICAL) {
            mText?.let {
                val baseLine = height / 2 + dy - mTextSize * textLength / 2
                val strings = it.split("\n").toTypedArray()
                for (j in strings.indices) {
                    for (i in strings[j].indices) {
                        val s = strings[j].substring(i, i + 1)
                        canvas.drawText(
                            s,
                            (paddingLeft + j * mTextSize).toFloat(),
                            (baseLine + i * mTextSize + paddingTop).toFloat(),
                            mPaint
                        )
                    }
                }
            }

        } else {
            val baseLine = height / 2 + dy
            mText?.let { canvas.drawText(it, paddingLeft.toFloat(), baseLine.toFloat(), mPaint) }
        }
        //        for (int i = 0; i < mText.length(); i++) {
//            String s = mText.substring(i, i + 1);
//            if (i < 5)
//                canvas.drawText(s, 50, mTextSize + i * mTextSize, mPaint);
//            if (i > 5)
//                canvas.drawText(s, 50 + mTextSize, mTextSize + (i-6) * mTextSize, mPaint);
//        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_MOVE -> Log.e(TAG, "手指移动")
            MotionEvent.ACTION_DOWN -> Log.e(TAG, "手指按下")
            MotionEvent.ACTION_UP -> Log.e(TAG, "手指抬起")
        }
        return true
    }

    companion object {
        /**
         * 将sp值转换为px值，保证文字大小不变
         *
         * @param spValue
         * @return
         */
        fun sp2px(spValue: Int): Int {
            val fontScale = BaseApplication.context.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }
    }
    /**
     * 在布局layout中使用，但是会有style
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    /**
     * 在布局layout中使用
     *
     * @param context
     * @param attrs
     */
    /**
     * 构造函数会在代码new的时候调用
     *
     * @param context
     */
    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.VerticalTextView)
        mText = array.getString(R.styleable.VerticalTextView_textVertical)
        mTextSize = array.getDimensionPixelSize(
            R.styleable.VerticalTextView_textSizeVertical,
            sp2px(mTextSize)
        )
        mTextColor = array.getColor(R.styleable.VerticalTextView_textColorVertical, mTextColor)
        mOrientation = array.getInt(R.styleable.VerticalTextView_orientation, HORIZONTAL)
        //回收
        array.recycle()
        mPaint = Paint()
        //抗锯齿
        mPaint.isAntiAlias = true
        mPaint.textSize = mTextSize.toFloat()
        mPaint.color = mTextColor
        //设置宋体
        mPaint.typeface = getLiuGQTypeFace()
        //加粗
        mPaint.isFakeBoldText = true
    }
}