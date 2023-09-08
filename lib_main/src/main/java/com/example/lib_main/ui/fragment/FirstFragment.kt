package com.example.lib_main.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.constant.UserKeys
import com.example.lib_base.ext.init
import com.example.lib_base.ext.loadListData
import com.example.lib_base.utils.banner.BaseBannerData
import com.example.lib_base.utils.image.BigImageUtils
import com.example.lib_base.utils.image.GlideUtils
import com.example.lib_base.utils.qmui.QMUIStatusBarHelper
import com.example.lib_base.utils.ui.LayoutParamsUtils
import com.example.lib_base.utils.ui.TextFontUtils
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentFirstBinding
import com.example.lib_main.model.getSky
import com.example.lib_main.ui.adapter.ArticleAdapter
import com.example.lib_main.viewmodel.FirstViewModel
import com.hjq.toast.Toaster
import com.permissionx.guolindev.PermissionX
import com.stx.xhb.androidx.XBanner
import com.xuexiang.xqrcode.XQRCode
import com.xuexiang.xqrcode.ui.CaptureActivity
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction


/**
 * @CreateDate : 2020/12/31
 * @Author : 青柠
 * @Description :
 */
class FirstFragment : BaseFragment<FirstViewModel, FragmentFirstBinding>() {

    private val articleAdapter: ArticleAdapter by lazy { ArticleAdapter() }

    private val bannerData: MutableList<BaseBannerData> = ArrayList()

    private lateinit var startQRCodeLauncher: ActivityResultLauncher<Intent>

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

        startQRCodeLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                    handleScanResult(it.data)
                }
            }
    }

    override fun initData() {
        val imageList: MutableList<String> = ArrayList()
        imageList.run {
            add("http://pic1.win4000.com/m00/97/29/321baca0ae5c87225d48e14070219ea9.png")
            add("http://pic1.win4000.com/m00/c2/0f/7e41e8474463c32e80314970b7e5a2e2.jpg")
            add("http://pic1.win4000.com/wallpaper/2020-09-27/5f705c0022c79.jpg")
            add("http://pic1.win4000.com/m00/97/29/321baca0ae5c87225d48e14070219ea9.png")
        }
        for (i in 0..3) {
            val inData = BaseBannerData()
            inData.setImage(imageList[i])
            bannerData.add(inData)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun createObserver() {
        //首页文章列表监听
        mViewModel.articleDataState.observe(viewLifecycleOwner) {
            loadListData(mActivity, it, articleAdapter, mDatabind.refreshLayout)

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
                val data = getItem(position) ?: return@setOnItemClickListener
                nav().navigateAction(R.id.action_main_to_web, Bundle().apply {
                    putString("TITLE", data.title)
                    putString("URL", data.link)
                })
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
                    intentToPoetryDetail()
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
                    activity?.let {
                        if (!it.isDestroyed) {
                            GlideUtils.loadRoundImageTransform(
                                it,
                                R.drawable.banner_poetry_bg,
                                ivBanner,
                                12
                            )
                        }
                    }

                } else {
                    tvContent.visibility = View.GONE
                    activity?.let {
                        if (!it.isDestroyed) {
                            GlideUtils.loadRoundImageTransform(
                                it,
                                bannerData[position].getImage(),
                                ivBanner,
                                12
                            )
                        }
                    }
                }
            }
            setBannerData(R.layout.banner_main, bannerData)
        }

    }

    /**
     * 处理相机返回的二维码扫描结果
     *
     * @param data
     */
    private fun handleScanResult(data: Intent?) {
        data?.extras?.run {
            if (getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                val result = getString(XQRCode.RESULT_DATA)
                if (result != null) {
                    nav().navigateAction(R.id.action_main_to_web, Bundle().apply {
                        putString("TITLE", "")
                        putString("URL", result.toString())
                    })
                }
            } else if (getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {
                Toaster.show(getString(R.string.main_scan_fail_tips))
            }
        }
    }

    /**
     * 跳转到诗歌详情页
     */
    private fun intentToPoetryDetail() {
        //跳转并携带参数
        nav().navigateAction(R.id.action_main_to_poetry, Bundle().apply {
            putSerializable("DATA", mViewModel.poetryResultDataState.value)
        })
    }

    inner class ProxyClick {
        fun toScan() {
            //拍照、存储权限请求
            PermissionX.init(mActivity)
                .permissions(
                    Manifest.permission.CAMERA
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
                        val theme = com.xuexiang.xqrcode.R.style.XQRCodeTheme
                        val intent = Intent(mActivity, CaptureActivity::class.java)
                        intent.putExtra(CaptureActivity.KEY_CAPTURE_THEME, theme)
                        startQRCodeLauncher.launch(intent)
                    } else {
                        Toaster.show(getString(R.string.main_scan_permissions_fail))
                    }
                }
        }

        fun toSearch() {
            nav().navigateAction(R.id.action_main_to_search)
        }
    }

}