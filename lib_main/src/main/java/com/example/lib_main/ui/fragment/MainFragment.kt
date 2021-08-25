package com.example.lib_main.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.constant.RouterUrls
import com.example.lib_base.constant.StaticConstants
import com.example.lib_base.ext.init
import com.example.lib_base.ext.loadListData
import com.example.lib_base.router.RouterUtils
import com.example.lib_base.utils.banner.BaseBannerData
import com.example.lib_base.utils.image.BigImageUtils
import com.example.lib_base.utils.image.GlideUtils
import com.example.lib_main.R
import com.example.lib_main.adapter.BeautyAdapter
import com.example.lib_main.databinding.FragmentMainBinding
import com.example.lib_main.model.*
import com.example.lib_main.viewmodel.MainViewModel
import com.hjq.toast.ToastUtils
import com.permissionx.guolindev.PermissionX
import com.stx.xhb.androidx.XBanner
import com.xuexiang.xqrcode.XQRCode
import java.util.*


/**
 * @CreateDate : 2020/12/31
 * @Author : 青柠
 * @Description :
 */
class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {

    private val beautyAdapter: BeautyAdapter by lazy { BeautyAdapter() }
    private var beautyImageList = arrayListOf<String>()

    override fun layoutId(): Int {
        return R.layout.fragment_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click = ProxyClick()
        mDatabind.vm = mViewModel
        initAdapter()

        mDatabind.refreshLayout.autoRefresh()
        mDatabind.refreshLayout.setOnRefreshListener { refreshData(true) }
        mDatabind.refreshLayout.setOnLoadMoreListener { refreshData(false) }

        mDatabind.include.scrollLayout.setOnOverScrollReleaseListener {
            RouterUtils.intent(RouterUrls.ROUTER_URL_BEAUTY)
        }
    }

    override fun createObserver() {
        //妹子数据监听
        mViewModel.beautyDataState.observe(viewLifecycleOwner, {
            loadListData(it, beautyAdapter, mDatabind.refreshLayout)
            beautyImageList.clear()
            for (i in it.listData.indices) {
                beautyImageList.add(it.listData[i].images[0])
            }
        })

        //轮播图数据监听
        mViewModel.bannerDataState.observe(viewLifecycleOwner, {
            initBanner(it)
        })

        //天气数据监听
        mViewModel.weatherDataState.observe(viewLifecycleOwner, {
            mViewModel.isShowWeather.value = true
            mViewModel.weatherTemp.value = "${it.realtime.temperature.toInt()}℃"
            mViewModel.weatherSky.value = getSky(it.realtime.skycon).info

            //设置动画文件
            mDatabind.ltCloud.setAnimation(getSky(it.realtime.skycon).lottie)
            //执行动画
            mDatabind.ltCloud.playAnimation();

        })

    }

    private fun refreshData(isRefresh: Boolean) {
        if (isRefresh) {
            mViewModel.getBannerData()
            mViewModel.getIPLocation()
            mViewModel.getBeautyData(isRefresh)
        }

    }

    /**
     * 初始化适配器和点击事件
     */
    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mDatabind.include.rvBeauty.init(layoutManager, beautyAdapter, false)
        beautyAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                activity?.let { BigImageUtils.show(it, beautyImageList, position) }
            }
        }
    }

    /**
     * 初始化轮播图
     * @param data List<BaseBannerData>
     */
    private fun initBanner(data: List<BaseBannerData>) {
        mDatabind.include.xBanner.setOnItemClickListener { _: XBanner?, _: Any?, _: View?, position: Int ->
            RouterUtils.web(data[position].getUrl(), data[position].getTitle())
        }
        mDatabind.include.xBanner.loadImage { _: XBanner?, _: Any?, view: View?, position: Int ->
            GlideUtils.loadRoundImageTransform(data[position].getImage(), view as ImageView, 12)
        }
        mDatabind.include.xBanner.setAutoPlayAble(data.size > 1)
        mDatabind.include.xBanner.setBannerData(data)
    }


    /**
     * 相机扫描数据返回
     * @param requestCode Int
     * @param resultCode Int
     * @param data Intent?
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                StaticConstants.SCAN_REQUEST_CODE ->
                    handleScanResult(data)
            }
        }
    }

    /**
     * 处理相机返回的二维码扫描结果
     *
     * @param data
     */
    private fun handleScanResult(data: Intent?) {
        data?.run {
            val bundle = extras
            bundle?.run {
                if (getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                    val result = getString(XQRCode.RESULT_DATA)
                    if (result != null) {
                        RouterUtils.web(result.toString())
                    }
                } else if (getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {
                    ToastUtils.show(getString(R.string.main_scan_fail_tips))
                }
            }
        }
    }

    inner class ProxyClick {
        fun toScan() {
            //拍照、存储权限请求
            PermissionX.init(activity)
                .permissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .explainReasonBeforeRequest()
                .onExplainRequestReason { scope, deniedList ->
                    scope.showRequestReasonDialog(
                        deniedList, getString(R.string.main_scan_permissions_tips),
                        getString(R.string.main_ok), getString(R.string.main_cancel)
                    )
                }

                .request { allGranted, _, _ ->
                    if (allGranted) {
                        XQRCode.startScan(this@MainFragment, StaticConstants.SCAN_REQUEST_CODE)
                    } else {
                        ToastUtils.show(getString(R.string.main_scan_permissions_fail))
                    }
                }

        }

        fun toSearch() {
            RouterUtils.intent(RouterUrls.ROUTER_URL_SEARCH)
        }
    }

}

