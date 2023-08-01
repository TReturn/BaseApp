package com.example.lib_main.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.constant.RouterUrls
import com.example.lib_base.utils.ui.UiUtils
import com.example.lib_main.R
import com.example.lib_main.databinding.ActivityMpChartBinding
import com.example.lib_main.ui.widget.LastWeekFormattedValue
import com.example.lib_main.ui.widget.TempValueFormatter
import com.example.lib_main.viewmodel.MPChartViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar

/**
 * @CreateDate: 2023/3/30 16:26
 * @Author: 青柠
 * @Description: 图表库示例
 */
@Route(path = RouterUrls.ROUTER_URL_MP_CHART)
class MPChartActivity : BaseActivity<MPChartViewModel, ActivityMpChartBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()

        mDatabind.include.titleBar.title = getString(R.string.main_type_mp_chart)
        mDatabind.include.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                finish()
            }
        })

        //监听HorizontalScrollView滑动事件，联动温度折线图
        mDatabind.horizontalScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val nestedM = mDatabind.horizontalScrollView.measuredWidth
            val nestedCM = mDatabind.horizontalScrollView.getChildAt(0).measuredWidth
            //计算滑动比例
            val scrollProgress = scrollX / (nestedCM - nestedM).toFloat()

            //使用ScrollView控制折线图的左右移动
            mDatabind.lineChart.moveViewToX((scrollProgress * 100 * 0.24).toFloat())
        }
    }

    override fun initData() {
        //柱状图数据
        setBarChatData()
        //折线图数据
        setLineChartData()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun createObserver() {

    }

    /**
     * 柱状图
     */
    private fun setBarChatData() {
        //设置图表属性
        mDatabind.barChart.run {
            //禁用缩放
            setPinchZoom(false)
            setScaleEnabled(false)

            //隐藏左下角颜色指示器
            legend.isEnabled = false

            //隐藏右下角英文
            description.isEnabled = false

            // 隐藏右边 的坐标轴
            axisRight.isEnabled = false

            //XY两轴混合动画,参数是动画执行时间 毫秒为单位
            animateXY(2000, 2000)

            //设置X轴
            xAxis.run {
                //设置label在底下
                position = XAxis.XAxisPosition.BOTTOM
                //不设置竖型网格线
                setDrawGridLines(false)
                //设置底部数据
                valueFormatter = LastWeekFormattedValue()
            }
        }


        //设置数据
        val values = ArrayList<BarEntry>()

        for (i in 0..6) {
            val random = (100..3000).random()
            values.add(BarEntry(i.toFloat(), random.toFloat()))
        }

        val set: BarDataSet

        if (mDatabind.barChart.data != null &&
            mDatabind.barChart.data.dataSetCount > 0
        ) {
            set = mDatabind.barChart.data.getDataSetByIndex(0) as BarDataSet
            set.values = values
            mDatabind.barChart.data.notifyDataChanged()
            mDatabind.barChart.notifyDataSetChanged()
        } else {
            set = BarDataSet(values, "")
            //设置图表颜色
            set.setColors(Color.parseColor("#FD7622"))
            //是否绘制图表顶部数据
            set.setDrawValues(true)
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set)
            val data = BarData(dataSets)
            mDatabind.barChart.data = data
            mDatabind.barChart.setFitBars(true)
        }

        mDatabind.barChart.invalidate()
    }


    /**
     * 折线图
     */
    private fun setLineChartData() {

        mDatabind.lineChart.run {
            //禁用所有交互
            setTouchEnabled(false)

            //隐藏左下角颜色指示器
            legend.isEnabled = false

            //隐藏右下角英文
            description.isEnabled = false

            // 隐藏右边 的坐标轴
            axisRight.isEnabled = false
            // 隐藏左边 的坐标轴
            axisLeft.isEnabled = false

            //设置X轴
            xAxis.run {
                //不设置竖型网格线
                setDrawGridLines(false)
                //不绘制标签的横线
                setDrawAxisLine(false)
                //不绘制底部标签
                setDrawLabels(false)
            }

        }

        val values = ArrayList<Entry>()
        for (i in 0..23) {
            val random = (22..35).random()
            values.add(Entry(i.toFloat(), random.toFloat()))
        }

        val set: LineDataSet
        if (mDatabind.lineChart.data != null &&
            mDatabind.lineChart.data.dataSetCount > 0
        ) {
            set = mDatabind.lineChart.data.getDataSetByIndex(0) as LineDataSet
            set.values = values

            mDatabind.lineChart.data.notifyDataChanged()
            mDatabind.lineChart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set = LineDataSet(values, "DataSet")
            set.axisDependency = YAxis.AxisDependency.LEFT
            set.color = ColorTemplate.getHoloBlue()
            set.setCircleColor(Color.parseColor("#7CB9FF"))
            set.lineWidth = 2f
            set.circleRadius = 3f
            set.fillAlpha = 65
            set.fillColor = Color.parseColor("#A5CEFE")
            set.setDrawCircleHole(false)

            // create a data object with the data sets
            val data = LineData(set)
            data.setValueTextColor(Color.BLACK)
            data.setValueTextSize(14f)

            //弧度路径连接
            set.mode = LineDataSet.Mode.CUBIC_BEZIER
            //是否绘制图表顶部数据
            set.setDrawValues(true)

            //顶部数据格式
            set.valueFormatter = TempValueFormatter()

            // set data
            mDatabind.lineChart.data = data
            //设置x轴最多显示数据条数，（要在设置数据源后调用，否则是无效的）
            mDatabind.lineChart.setVisibleXRangeMaximum(4F)
        }
    }

    inner class ProxyClick {

    }

}