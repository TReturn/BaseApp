package com.example.lib_main.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import com.example.lib_base.base.BaseFragment
import com.example.lib_main.databinding.FragmentMainBinding
import com.example.lib_main.ui.widget.LastWeekFormattedValue
import com.example.lib_main.viewmodel.MainViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet


/**
 * @CreateDate : 2020/12/31
 * @Author : 青柠
 * @Description :
 */
class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {


    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click = ProxyClick()
        mDatabind.vm = mViewModel

    }

    override fun initData() {
        setBarChatData()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun createObserver() {

    }

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

        val set1: BarDataSet

        if (mDatabind.barChart.data != null &&
            mDatabind.barChart.data.dataSetCount > 0
        ) {
            set1 = mDatabind.barChart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            mDatabind.barChart.data.notifyDataChanged()
            mDatabind.barChart.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "")
            //设置图表颜色
            set1.setColors(Color.parseColor("#FD7622"))
            //是否绘制图表顶部数据
            set1.setDrawValues(true)
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)
            val data = BarData(dataSets)
            mDatabind.barChart.data = data
            mDatabind.barChart.setFitBars(true)
        }

        mDatabind.barChart.invalidate()
    }

    inner class ProxyClick {
        fun toScan() {

        }
    }

}

