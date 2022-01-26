package com.example.lib_main.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.listener.GridSpanSizeLookup
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.constant.RouterUrls
import com.example.lib_base.constant.StaticConstants
import com.example.lib_base.ext.init
import com.example.lib_base.ext.loadListData
import com.example.lib_base.router.RouterUtils
import com.example.lib_base.utils.banner.BaseBannerData
import com.example.lib_base.utils.image.BigImageUtils
import com.example.lib_base.utils.image.GlideUtils
import com.example.lib_base.utils.qmui.QMUIStatusBarHelper
import com.example.lib_base.utils.ui.LayoutParamsUtils
import com.example.lib_base.utils.ui.TextFontUtils
import com.example.lib_main.R
import com.example.lib_main.adapter.ArticleAdapter
import com.example.lib_main.adapter.DataBindingAdapter
import com.example.lib_main.databinding.FragmentMainBinding
import com.example.lib_main.model.*
import com.example.lib_main.viewmodel.MainViewModel
import com.hjq.toast.ToastUtils
import com.permissionx.guolindev.PermissionX
import com.stx.xhb.androidx.XBanner
import com.xuexiang.xqrcode.XQRCode


/**
 * @CreateDate : 2020/12/31
 * @Author : 青柠
 * @Description :
 */
class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {

    //private val articleAdapter: ArticleAdapter by lazy { ArticleAdapter() }
    private val articleAdapter: DataBindingAdapter by lazy { DataBindingAdapter() }

    private val bannerData: MutableList<BaseBannerData> = ArrayList()

    override fun layoutId(): Int {
        return R.layout.fragment_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click = ProxyClick()
        mDatabind.vm = mViewModel

        LayoutParamsUtils.setHeight(
            mDatabind.flTranslucent,
            QMUIStatusBarHelper.getStatusbarHeight(activity)
        )
        initAdapter()
        mDatabind.refreshLayout.autoRefresh()
        mDatabind.refreshLayout.setOnRefreshListener { refreshData(true) }
        mDatabind.refreshLayout.setOnLoadMoreListener { refreshData(false) }

//        mDatabind.include.scrollLayout.setOnOverScrollReleaseListener {
//            RouterUtils.intent(RouterUrls.ROUTER_URL_BEAUTY)
//        }
    }

    override fun initData() {
        val imageList: MutableList<String> = ArrayList()
        imageList.run {
            add("http://pic1.win4000.com/m00/97/29/321baca0ae5c87225d48e14070219ea9.png")
            add("http://pic1.win4000.com/m00/c2/0f/7e41e8474463c32e80314970b7e5a2e2.jpg")
            add("http://pic1.win4000.com/wallpaper/2020-09-27/5f705c0022c79.jpg")
            add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.netbian.com%2Fuploads%2Fallimg%2F210704%2F215614-16254069745605.jpg")
            add("http://pic1.win4000.com/m00/97/29/321baca0ae5c87225d48e14070219ea9.png")
        }
        for (i in 0..4) {
            val inData = BaseBannerData()
            inData.setImage(imageList[i])
            bannerData.add(inData)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun createObserver() {
        //首页文章列表监听
        mViewModel.articleDataState.observe(viewLifecycleOwner) {
            loadListData(it, articleAdapter, mDatabind.refreshLayout)

        }

        //古诗词数据监听
        mViewModel.poetryResultDataState.observe(viewLifecycleOwner) {
            initBanner(it.content, "《${it.origin.title}》${it.origin.author}")
        }

        //天气数据监听
        mViewModel.weatherDataState.observe(viewLifecycleOwner) {
            mViewModel.isShowWeather.value = true
            mViewModel.weatherTemp.value = "${it.realtime.temperature.toInt()}℃"
            mViewModel.weatherSky.value = getSky(it.realtime.skycon).info

            //设置动画文件
            mDatabind.ltCloud.setAnimation(getSky(it.realtime.skycon).lottie)
            //执行动画
            mDatabind.ltCloud.playAnimation();

        }
    }

    private fun refreshData(isRefresh: Boolean) {
        if (isRefresh) {
            mViewModel.getPoetry()
            mViewModel.getIPLocation()
            mViewModel.getArticleData(isRefresh)
        } else {
            mViewModel.getArticleData(isRefresh)
        }

    }

    /**
     * 初始化适配器和点击事件
     */
    private fun initAdapter() {
        mDatabind.include.rvArticle.init(GridLayoutManager(activity, 1), articleAdapter, false)
        articleAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                RouterUtils.web(data[position].link, data[position].title)
            }
        }
    }

    /**
     * 初始化轮播图
     * @param poetry String
     */
    private fun initBanner(poetry: String, author: String) {

        mDatabind.include.xBanner.run {
            setOnItemClickListener { _: XBanner?, _: Any?, _: View?, position: Int ->
                if (position == 0) {

                } else {
                    activity?.let { BigImageUtils.show(it, (bannerData[position].getImage())) }
                }
            }

            loadImage { banner, model, view, position ->
                val tvContent = view.findViewById<View>(R.id.tvContent) as TextView
                val tvAuthor = view.findViewById<View>(R.id.tvAuthor) as TextView
                val ivBanner = view.findViewById<View>(R.id.ivBanner) as ImageView
                if (position == 0) {
                    TextFontUtils.load(TextFontUtils.getLiuGQTypeFace(), tvContent, tvAuthor)
                    tvContent.text = poetry
                    tvAuthor.text = author
                    tvContent.visibility = View.VISIBLE
                    tvAuthor.visibility = View.VISIBLE
                    GlideUtils.loadRoundImageTransform(
                        activity,
                        R.drawable.banner_poetry_bg,
                        ivBanner,
                        12
                    )
                } else {
                    tvContent.visibility = View.GONE
                    GlideUtils.loadRoundImageTransform(
                        activity,
                        bannerData[position].getImage(),
                        ivBanner,
                        12
                    )
                }
            }
            setAutoPlayAble(bannerData.size > 1)
            setBannerData(R.layout.banner_main, bannerData)
        }

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

