package com.example.lib_main.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.constant.RouterUrls
import com.example.lib_base.ext.init
import com.example.lib_base.router.RouterUtils
import com.example.lib_base.utils.qmui.QMUIStatusBarHelper
import com.example.lib_base.utils.ui.LayoutParamsUtils
import com.example.lib_base.utils.ui.UiUtils
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentSecondBinding
import com.example.lib_main.model.DemoTypeModel
import com.example.lib_main.ui.adapter.DemoTypeAdapter
import com.example.lib_main.viewmodel.SecondViewModel


/**
 * @CreateDate : 2020/12/31
 * @Author : 青柠
 * @Description :
 */
class SecondFragment : BaseFragment<SecondViewModel, FragmentSecondBinding>() {

    private val typeAdapter: DemoTypeAdapter by lazy { DemoTypeAdapter() }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click = ProxyClick()
        mDatabind.vm = mViewModel

        LayoutParamsUtils.setHeight(
            mDatabind.flTranslucent,
            QMUIStatusBarHelper.getStatusbarHeight(activity)
        )

        initAdapter()
    }

    override fun initData() {
        val typeList: MutableList<DemoTypeModel> = arrayListOf()
        typeAdapter.setList(
            typeList.apply {
                add(
                    DemoTypeModel(
                        UiUtils.getString(R.string.main_type_mp_chart),
                        R.drawable.ic_mp_chart
                    )
                )
                add(
                    DemoTypeModel(
                        UiUtils.getString(R.string.main_type_attribute_animation),
                        R.drawable.ic_attribute_animation
                    )
                )
            }
        )
    }

    /**
     * 初始化适配器和点击事件
     */
    private fun initAdapter() {
        mDatabind.rvType.init(GridLayoutManager(mActivity, 3), typeAdapter, false)
        typeAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                when (data[position].title) {
                    UiUtils.getString(R.string.main_type_mp_chart) -> {
                        RouterUtils.intent(RouterUrls.ROUTER_URL_MP_CHART)
                    }
                    UiUtils.getString(R.string.main_type_attribute_animation) -> {
                        RouterUtils.intent(RouterUrls.ROUTER_URL_ATT_ANIMATION)
                    }
                }
            }
        }
    }

    override fun createObserver() {

    }


    inner class ProxyClick {

    }

}

