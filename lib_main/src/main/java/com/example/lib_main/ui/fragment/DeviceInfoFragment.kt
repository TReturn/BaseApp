package com.example.lib_main.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.DeviceUtils
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.ext.init
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentDeviceInfoBinding
import com.example.lib_main.databinding.FragmentSecondBinding
import com.example.lib_main.model.DemoTypeModel
import com.example.lib_main.model.DeviceInfoModel
import com.example.lib_main.ui.adapter.DemoTypeAdapter
import com.example.lib_main.ui.adapter.DeviceInfoAdapter
import com.example.lib_main.viewmodel.DeviceInfoViewModel
import com.example.lib_main.viewmodel.SecondViewModel
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

/**
 * @CreateDate: 2023/8/25 17:24
 * @Author: 青柠
 * @Description:
 */
class DeviceInfoFragment : BaseFragment<DeviceInfoViewModel, FragmentDeviceInfoBinding>() {

    private val deviceInfoAdapter: DeviceInfoAdapter by lazy { DeviceInfoAdapter() }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        setTranslucent(mDatabind.flTranslucent)

        initAdapter()

        mDatabind.include.titleBar.title = getString(R.string.main_type_device)
        mDatabind.include.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                nav().navigateUp()
            }
        })
    }

    @SuppressLint("MissingPermission")
    override fun initData() {
        val typeList: MutableList<DeviceInfoModel> = arrayListOf()
        deviceInfoAdapter.setList(
            typeList.apply {
                add(DeviceInfoModel("判断设备是否 rooted", DeviceUtils.isDeviceRooted().toString()))
                add(DeviceInfoModel("判断设备 ADB 是否可用", DeviceUtils.isAdbEnabled().toString()))
                add(DeviceInfoModel("获取设备系统版本号", DeviceUtils.getSDKVersionName()))
                add(
                    DeviceInfoModel(
                        "获取设备系统版本码",
                        DeviceUtils.getSDKVersionCode().toString()
                    )
                )
                add(DeviceInfoModel("获取设备 AndroidID", DeviceUtils.getAndroidID().toString()))
                add(DeviceInfoModel("获取设备 MAC 地址", DeviceUtils.getMacAddress().toString()))
                add(DeviceInfoModel("获取设备厂商", DeviceUtils.getManufacturer()))
                add(DeviceInfoModel("获取设备型号", DeviceUtils.getModel()))
                add(DeviceInfoModel("获取设备 ABIs", getABIs()))
                add(DeviceInfoModel("判断是否是平板", DeviceUtils.isTablet().toString()))
                add(DeviceInfoModel("判断是否是模拟器", DeviceUtils.isEmulator().toString()))
                add(
                    DeviceInfoModel(
                        "开发者选项是否打开",
                        DeviceUtils.isDevelopmentSettingsEnabled().toString()
                    )
                )
                add(DeviceInfoModel("获取唯一设备 ID", DeviceUtils.getUniqueDeviceId()))
                add(
                    DeviceInfoModel(
                        "判断是否同一设备",
                        DeviceUtils.isSameDevice(DeviceUtils.getUniqueDeviceId()).toString()
                    )
                )
            }
        )
    }

    /**
     * 初始化适配器和点击事件
     */
    private fun initAdapter() {
        mDatabind.rvDevice.init(GridLayoutManager(mActivity, 1), deviceInfoAdapter, false)
    }

    /**
     * CPU架构
     */
    private fun getABIs(): String {
        var abis = ""
        for (i in DeviceUtils.getABIs()) {
            abis += "$i\t\t"
        }
        return abis
    }

}