package com.example.lib_main.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.ext.init
import com.example.lib_base.utils.ui.UiUtils
import com.example.lib_base.widget.SlideImageView
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentSecondBinding
import com.example.lib_main.model.DemoTypeModel
import com.example.lib_main.ui.adapter.DemoTypeAdapter
import com.example.lib_main.viewmodel.SecondViewModel
import com.hjq.toast.Toaster
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction


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
        setTranslucent(mDatabind.flTranslucent)

        initAdapter()

        //首页自动贴边View点击事件
        mDatabind.slFruitImage.setOnDragViewClickListener(object :
            SlideImageView.OnDrawViewClickListener {
            override fun onDragViewClick() {
                ProxyClick().toFruit()
            }
        })
    }

    override fun initData() {
        val typeList: MutableList<DemoTypeModel> = arrayListOf()
        typeAdapter.setList(
            typeList.apply {
                add(
                    DemoTypeModel(
                        getString(R.string.main_type_mp_chart),
                        R.drawable.ic_mp_chart
                    )
                )
                add(
                    DemoTypeModel(
                        getString(R.string.main_type_attribute_animation),
                        R.drawable.ic_attribute_animation
                    )
                )
                add(
                    DemoTypeModel(
                        getString(R.string.main_type_note),
                        R.drawable.ic_note
                    )
                )
                add(
                    DemoTypeModel(
                        getString(R.string.main_type_device),
                        R.drawable.ic_device
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
                    getString(R.string.main_type_mp_chart) -> {
                        nav().navigateAction(R.id.action_main_to_mp_chart)
                    }

                    getString(R.string.main_type_attribute_animation) -> {
                        nav().navigateAction(R.id.action_main_to_animation)
                    }

                    getString(R.string.main_type_note) -> {
                        nav().navigateAction(R.id.action_main_to_note)
                    }
                    getString(R.string.main_type_device) -> {
                        nav().navigateAction(R.id.action_main_to_device)
                    }
                }
            }
        }
    }

    override fun createObserver() {

    }


    inner class ProxyClick {
        fun toFruit() {
            Toaster.show("Lemon")
        }

        fun toFruitDel() {
            mViewModel.isShowFruitView.value = false
        }
    }

}

