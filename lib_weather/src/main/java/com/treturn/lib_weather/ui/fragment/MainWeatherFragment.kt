package com.treturn.lib_weather.ui.fragment

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.lib_base.appViewModel
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.ext.bindViewPager2
import com.example.lib_base.ext.init
import com.example.lib_base.magic.ScaleCircleNavigator
import com.example.lib_base.model.SunRiseAndSetModel
import com.example.lib_base.utils.data.MMKVUtils
import com.example.lib_base.utils.image.GlideUtils
import com.example.lib_base.utils.qmui.QMUIStatusBarHelper
import com.example.lib_base.utils.ui.LayoutParamsUtils
import com.hjq.toast.ToastUtils
import com.lxj.xpopup.core.BasePopupView
import com.treturn.lib_weather.R
import com.treturn.lib_weather.constont.WeatherKeys
import com.treturn.lib_weather.databinding.FragmentMainWeatherBinding
import com.treturn.lib_weather.ui.customview.PlaceholderLinearHelper
import com.treturn.lib_weather.utils.WeatherPicUtils
import com.treturn.lib_weather.utils.getSky
import com.treturn.lib_weather.viewmodel.MainWeatherViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.hgj.jetpackmvvm.base.Ktx
import me.hgj.jetpackmvvm.network.NetworkUtil
import me.samlss.broccoli.Broccoli
import net.lucode.hackware.magicindicator.abs.IPagerNavigator

/**
 * @CreateDate: 2023/6/15 15:26
 * @Author: 青柠
 * @Description:
 */


class MainWeatherFragment :
    BaseFragment<MainWeatherViewModel, FragmentMainWeatherBinding>() {

    //fragment集合
    private var fragmentList: ArrayList<Fragment> = arrayListOf()

    //是否是定位城市
    private var weatherIsLocation: ArrayList<Int> = arrayListOf()

    //城市天气
    private var weatherCityText: ArrayList<String> = arrayListOf()

    private var vpPosition = 0

    private var isShowMagicIndicator = true

    //城市管理列表点击的Position
    private var weatherSelectPosition = -1

    //城市列表是否已改变
    private var isChangeWeatherSelect = false

    //跳转到新增城市（即最后一个）
    private var isAddNewWeatherCity = false

    private var adTopDialog: BasePopupView? = null

    //每次打开显示一次
    private var isShowTopAd = false

    //是否有定位城市
    private var isHaveLocation = false

    //动效相关
    private var imageAssetsFolder = ""
    private var imageAssetsJson = ""

    //上一个Tab天气图片
    private var lastWeatherPic: Int = 0

    private var selectCityPosition = 0

    private var mBroccoli: Broccoli? = null

    private var enterTimes = 0

    //是否已创建好ViewPager
    private var isInitViewPager = false

    //已添加城市是否有变动 1.新增 2.新增定位城市 3.删除
    private var cityListChangeType = 1

    private var circleNavigator: IPagerNavigator? = null

    private var nowSelectAreaCode: MutableList<String> = arrayListOf()
    private var nowSelectCityName: MutableList<String> = arrayListOf()
    private var nowSelectLonLat: MutableList<String> = arrayListOf()

    //申请定位权限
    private var locationPermissionLauncher: ActivityResultLauncher<Array<String>>? = null
    private var settingLauncher: ActivityResultLauncher<Intent>? = null

    //GPS回调
    private var gpsLauncher: ActivityResultLauncher<Intent>? = null

    //定位管理器，用于判断用户是否打开GPS
    private var locationManager: LocationManager? = null

    //未开启定位服务：1没有权限；2没有打开GPS
    private var locationFailType = 1

    //仅在首次进入开屏时定位一次
    private var isFirstCheckLocation = false

    //仅在首次刷新进时再定位一次
    private var isFirstRefreshLocation = false


    @SuppressLint("ClickableViewAccessibility")
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()

        initNavigator()
        initPlaceholders()
        initWeatherBg()
        initLocationLauncher()

        //设置沉浸
        activity?.let {
            QMUIStatusBarHelper.translucent(it)
            LayoutParamsUtils.setHeight(
                mDatabind.flTranslucent,
                QMUIStatusBarHelper.getStatusbarHeight(it)
            )
            locationManager = it.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }

        mDatabind.refreshLayout.setOnRefreshListener {
            appViewModel.isRefreshWeatherData.value = true
            mDatabind.refreshLayout.finishRefresh(6000)
            if (!isFirstRefreshLocation) {
                checkLocation(true)
                isFirstRefreshLocation = true
            }
            if (!NetworkUtil.isNetworkAvailable(mActivity)) {
                mViewModel.isShowRefreshLayout.value = true
            }
        }

    }

    override fun initData() {

    }

    private fun initWeatherBg() {
        val weatherPic = MMKVUtils.getInt(
            WeatherKeys.WEATHER_MAIN_FIRST_CITY_BG,
            R.drawable.pic_main_weather_qingtian
        )
        GlideUtils.loadImageProtist(activity, weatherPic, mDatabind.ivWeatherBg)
        GlideUtils.loadImageProtist(activity, weatherPic, mDatabind.ivWeatherBg2)
        //透明度：alpha
        ObjectAnimator.ofFloat(mDatabind.ivWeatherBg2, "alpha", 1F, 0F).run {
            duration = 2000
            start()
        }
    }

    override fun onResume() {
        super.onResume()

        //是否有定位城市
        isHaveLocation = MMKVUtils.getBool(WeatherKeys.IS_HAVE_LOCATION)

        //仅在首次进入开屏时定位一次
        if (!isFirstCheckLocation) {
            //检查是否开启定位
            checkLocation(false)
            isFirstCheckLocation = true
        }

        //检测网络状态
        if (!NetworkUtil.isNetworkAvailable(mActivity)) {
            mViewModel.isShowRefreshLayout.value = true
        }

    }


    /**
     * 加载骨架屏
     */
    private fun initPlaceholders() {
        mBroccoli = Broccoli()
        mBroccoli?.let {
            it.addPlaceholders(
                PlaceholderLinearHelper.getParameter(
                    it,
                    mDatabind.includeWeather.ivBroccoli1, mDatabind.includeWeather.ivBroccoli2,
                    mDatabind.includeWeather.tvBroccoli1, mDatabind.includeWeather.tvBroccoli2,
                    mDatabind.includeWeather.tvBroccoli3, mDatabind.includeWeather.tvBroccoli4,
                    mDatabind.includeWeather.tvBroccoli5, mDatabind.includeWeather.tvBroccoli6,
                    mDatabind.includeWeather.tvBroccoli7, mDatabind.includeWeather.tvBroccoli8,
                )
            )
            it.show()
        }
    }

    override fun createObserver() {
        super.createObserver()


        //关闭城市管理页面后刷新数据
        appViewModel.isFinishCityManager.observeInFragment(this) {

            appViewModel.isAddNewWeatherCity.postValue(true)
        }

        //骨架屏
        appViewModel.isShowIncludeBg.observeInFragment(this) {
            if (mViewModel.isShowIncludeBg.value != it) {
                mViewModel.isShowIncludeBg.value = it
            }
            mViewModel.isShowWeatherViewPager.value = !it
        }


        //切换天气Tab改变相应的背景图和动效
        appViewModel.changeMainWeatherPic.observeInFragment(this) {
            val newWeatherPic = WeatherPicUtils.get(it).picResId
            if (!TextUtils.isEmpty(it.weatherText) && lastWeatherPic != newWeatherPic) {
                //透明度：alpha
                ObjectAnimator.ofFloat(mDatabind.ivWeatherBg2, "alpha", 1F, 0F).run {
                    duration = 1800
                    start()
                }
                ObjectAnimator.ofFloat(mDatabind.lottieWeather, "alpha", 0F, 1F).run {
                    duration = 1800
                    start()
                }
                setWeatherPic(it)
                lastWeatherPic = newWeatherPic
            }
        }

        //是否结束刷新
        appViewModel.isFinishRefreshWeatherData.observeInFragment(this) {
            if (mDatabind.refreshLayout.isRefreshing) {
                mDatabind.refreshLayout.finishRefresh()
                if (mViewModel.refreshWeatherTime.value == "当前网络不可用 请稍后重试") {
                    mViewModel.isShowRefreshLayout.value = false
                }
            }
        }

        //天气几分钟前刷新
        appViewModel.refreshWeatherText.observeInFragment(this) {
            mViewModel.refreshWeatherTime.value = it
            mViewModel.isShowRefreshLayout.value = true
            lifecycleScope.launch {
                delay(2000)
                if (!isFragmentDestroy() && !isLocationStatusShow()) {
                    mViewModel.isShowRefreshLayout.postValue(false)
                }
            }

        }


        //是否拦截资讯子View事件
        appViewModel.isSlideNews.observeInFragment(this) {
            mDatabind.vpWeather.isUserInputEnabled = it
            mDatabind.refreshLayout.setEnableRefresh(it)
        }

        //是否开启刷新天气（折叠后关闭刷新，完全展开再开启，避免事件冲突）
        appViewModel.isEnableRefreshWeather.observeInFragment(this) {
            mDatabind.refreshLayout.setEnableRefresh(it)
        }

        mViewModel.isShowRefreshLayout.observe(viewLifecycleOwner) {
            if (NetworkUtil.isNetworkAvailable(mActivity)) {
                //显示打开定位提示
                if (isLocationStatusShow()) {
                    mViewModel.refreshWeatherTime.value = "无法定位，去开启定位服务"
                    mViewModel.isShowLocationLayout.value = true
                    GlideUtils.loadImageProtist(
                        mActivity,
                        R.drawable.ic_check_location,
                        mDatabind.ivMainRefreshStatus
                    )
                    lifecycleScope.launch {
                        delay(100)
                        if (!isFragmentDestroy()) {
                            mDatabind.llRefreshStatus.visibility = View.VISIBLE
                        }
                    }

                } else {
                    //刷新成功
                    mViewModel.isShowLocationLayout.value = false
                    GlideUtils.loadImageProtist(
                        mActivity,
                        R.drawable.ic_main_refresh_weather,
                        mDatabind.ivMainRefreshStatus
                    )
                }
            } else {
                //没有网络
                mViewModel.isShowLocationLayout.value = false
                mViewModel.refreshWeatherTime.value = "当前网络不可用 请稍后重试"
                GlideUtils.loadImageProtist(
                    mActivity,
                    R.drawable.ic_refresh_fail_weather,
                    mDatabind.ivMainRefreshStatus
                )
            }
        }

        //监听折叠状态
        appViewModel.mainScrimsStatus.observeInFragment(this) {
            mViewModel.isShowCityManger.value = !it
            mViewModel.isShowMainWeatherBG.value = !it
            mViewModel.isShowMainWeatherLottie.value = !it

            if (isShowMagicIndicator) {
                mViewModel.isShowMagicIndicator.value = !it
            }
            if (it) {
                mViewModel.isShowRefreshLayout.value = false
                mDatabind.clParent.setBackgroundColor(Color.parseColor("#2B8DFB"))
            } else {
                mDatabind.clParent.setBackgroundColor(Color.parseColor("#EEF5FE"))
            }
        }

        //折叠后展示天气图标
        appViewModel.mainWeatherText.observeInFragment(this) {
            GlideUtils.loadImageProtist(
                activity,
                getSky(it).resId,
                mDatabind.ivCityWeather
            )
        }

        //已添加城市是否有变动
        appViewModel.cityListChangeType.observeInFragment(this) {
            cityListChangeType = it
            isChangeWeatherSelect = true

            //mViewModel.requestCitySelect()

            lifecycleScope.launch {
                delay(1800)
                if (!isFragmentDestroy() && mViewModel.isShowIncludeBg.value == true) {
                    appViewModel.isShowIncludeBg.postValue(false)
                }
            }
            if (!MMKVUtils.getBool(WeatherKeys.IS_USER_ADD_CITY)) {
                MMKVUtils.put(WeatherKeys.IS_USER_ADD_CITY, true)
            }
        }

        //讯飞语音播报
        appViewModel.playIFlyTekCloudVoice.observeInFragment(this) {
            //startPlayIFlyVoice()
        }

        appViewModel.stopIFlyTekCloudVoice.observeInFragment(this) {
            //IFlyCloudVoiceUtils.stopSpeaking()
        }


        // 跳转到指定城市
        appViewModel.weatherSelectPosition.observeInFragment(this) {
            weatherSelectPosition = it
            //城市列表未改变（新增、删除）直接跳转
            if (!isChangeWeatherSelect) {
                mDatabind.vpWeather.currentItem = it
            }
        }

        // 跳转到新增城市（即最后一个）
        appViewModel.isAddNewWeatherCity.observeInFragment(this) {
            isAddNewWeatherCity = it
        }

    }

    private fun addViewPager2Data(
        areaCode: String,
        cityName: String,
        lonLat: String,
        isDw: Int,
    ) {
//        fragmentList.add(
//            WeatherDetailFragment.newInstance(
//                areaCode,
//                cityName,
//                lonLat,
//                isDw,
//                vpPosition
//            )
//        )
        vpPosition += 1
    }

    private fun initViewPager2() {
        if (isFragmentDestroy() || fragmentList.isEmpty() || circleNavigator == null) {
            return
        }
        //初始化viewpager2
        mDatabind.vpWeather.init(this, fragmentList)
        //初始化magicIndicator
        mDatabind.magicIndicator.bindViewPager2(mDatabind.vpWeather, circleNavigator) {
            selectCityPosition = it
            mViewModel.isShowRefreshLayout.value = false
            setMainTopAddress(it)

            mViewModel.isShowLocationImage.value = weatherIsLocation[it] == 1
            val drawable = ContextCompat.getDrawable(mActivity, R.drawable.ic_location_city)
            drawable?.apply {
                setBounds(0, 0, minimumWidth, minimumHeight)
                if (weatherIsLocation[it] == 1) {
                    mDatabind.tvAddress.setCompoundDrawables(null, null, this, null)
                } else {
                    mDatabind.tvAddress.setCompoundDrawables(null, null, null, null)
                }
            }

            //保存当前统一显示选中的城市名
            appViewModel.currentUnifyWeatherCityName.value = nowSelectCityName[it]
            //保存当前选中的城市是否定位的
            appViewModel.currentIsLocationCityName.value = it == 0

            mViewModel.isShowLocationImage.value = weatherIsLocation[it] == 1

            //存储城市名、经纬度、城市Code、是否为定位城市
            mViewModel.address.value?.run {
                MMKVUtils.put(WeatherKeys.NOW_SELECT_CITY_NAME, this)
            }
            MMKVUtils.put(WeatherKeys.NOW_SELECT_CITY_LONLAT, nowSelectLonLat[it])
            MMKVUtils.put(WeatherKeys.NOW_SELECT_CITY_AREA_CODE, nowSelectAreaCode[it])
            MMKVUtils.put(WeatherKeys.NOW_SELECT_CITY_IS_LOCATION, it == 0)

        }

        vpOffScreenPageLimit()
        isInitViewPager = true
        (circleNavigator as ScaleCircleNavigator).setCircleCount(fragmentList.size)
        circleNavigator?.notifyDataSetChanged()
    }

    /**
     * 初始化定位权限
     */
    private fun initLocationLauncher() {
        locationPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                var isGranted = true
                for (value in it.values) {
                    if (!value) {
                        isGranted = false
                        break
                    }
                }
                if (isGranted) {
                    checkLocation(true)
                } else {
                    ToastUtils.show("缺少定位权限，请手动添加！")
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            mActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                        || !ActivityCompat.shouldShowRequestPermissionRationale(
                            mActivity,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    ) {
                        //引导用户到设置中去进行设置
                        try {
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            intent.data = Uri.fromParts("package", mActivity.packageName, null)
                            settingLauncher?.launch(intent)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }

        settingLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (ActivityCompat.checkSelfPermission(
                        mActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    checkLocation(true)
                }
            }

        gpsLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                checkLocation(true)
            }
    }

    /**
     * 初始化ViewPager圆点
     */
    private fun initNavigator() {
        circleNavigator = ScaleCircleNavigator(Ktx.app)
        (circleNavigator as ScaleCircleNavigator).setNormalCircleColor(Color.parseColor("#4DFFFFFF"))
        (circleNavigator as ScaleCircleNavigator).setSelectedCircleColor(Color.WHITE)
    }

    /**
     * 设置预加载页数
     */
    private fun vpOffScreenPageLimit() {
        mDatabind.vpWeather.isUserInputEnabled = true

        if (fragmentList.size >= 8) {
            mDatabind.vpWeather.offscreenPageLimit = 3
        } else if (fragmentList.size >= 4) {
            mDatabind.vpWeather.offscreenPageLimit = 2
        } else {
            mDatabind.vpWeather.offscreenPageLimit = 1
        }
    }

    /**
     * 首页顶部定位城市名称
     * @param position Int
     */
    private fun setMainTopAddress(position: Int) {
        mViewModel.address.value = nowSelectCityName[position]
    }

    override fun onPause() {
        super.onPause()
        isChangeWeatherSelect = false
        isAddNewWeatherCity = false
        weatherSelectPosition = -1
    }


    private fun setDataBaseList() {

        if (isInitViewPager) {
            //不重新创建ViewPager，通过notifyDataSetChanged通知更新，处理城市名、数据、小圆点选中等情况

            if (cityListChangeType == 1) {
                //处理删除、添加等操作后，原城市数量跟现城市数量相同的情况
                mDatabind.vpWeather.setCurrentItem(vpPosition - 1, false)
                setMainTopAddress(vpPosition - 1)
            } else if (cityListChangeType == 2 || cityListChangeType == 3) {
                //处理删除城市、新增定位城市的情况，跳转到第一个TAB
                mDatabind.vpWeather.setCurrentItem(0, false)
                setMainTopAddress(0)
            }

            //列表无变化，跳转到选择的列表
            if (isChangeWeatherSelect && weatherSelectPosition != -1) {
                mDatabind.vpWeather.setCurrentItem(weatherSelectPosition, false)
            }
            mDatabind.vpWeather.adapter?.notifyDataSetChanged()

            //更新小圆点选中
            (circleNavigator as ScaleCircleNavigator).setCircleCount(vpPosition)
            circleNavigator?.notifyDataSetChanged()
        } else {
            //创建ViewPager
            initViewPager2()
        }
    }


    /**
     * 开启一次定位刷新
     */
    private fun checkLocation(isRefresh: Boolean) {

        if (!isRefresh) {
            if (MMKVUtils.getBool(WeatherKeys.IS_FIRST_LOCATION, true)) {
                MMKVUtils.put(WeatherKeys.IS_FIRST_LOCATION, false)
                return
            }
        }

        if (!isHaveLocation || selectCityPosition != 0) {
            return
        }

        MMKVUtils.put(WeatherKeys.LOCATION_SUCCESS_TIME, System.currentTimeMillis())
    }


    private fun checkLoadLottie() {
        if (!TextUtils.isEmpty(imageAssetsFolder) && !TextUtils.isEmpty(imageAssetsJson) && isResumed) {
            mViewModel.isShowMainWeatherLottie.value = true
            mDatabind.lottieWeather.imageAssetsFolder = imageAssetsFolder
            mDatabind.lottieWeather.setAnimation(imageAssetsJson)
            if (!mDatabind.lottieWeather.isAnimating) {
                mDatabind.lottieWeather.playAnimation()
            }
        } else {
            mViewModel.isShowMainWeatherLottie.value = false
            mDatabind.lottieWeather.cancelAnimation()
        }
    }

    /**
     * 对应天气设置背景图
     * @param model 天气名称，日出日落时间
     */
    private fun setWeatherPic(model: SunRiseAndSetModel) {
        val weatherPicModel = WeatherPicUtils.get(model)

        imageAssetsFolder = weatherPicModel.imageAssetsFolder
        imageAssetsJson = weatherPicModel.imageAssetsJson

        GlideUtils.loadImageProtist(activity, weatherPicModel.picResId, mDatabind.ivWeatherBg)
        lifecycleScope.launch {
            delay(2000)
            if (!isFragmentDestroy()) {
                GlideUtils.loadImageProtist(
                    activity,
                    weatherPicModel.picResId,
                    mDatabind.ivWeatherBg2
                )
            }
        }
        checkLoadLottie()

        if (mDatabind.vpWeather.currentItem == 0) {
            MMKVUtils.put(WeatherKeys.WEATHER_MAIN_FIRST_CITY_BG, weatherPicModel.picResId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        isShowTopAd = false
        locationManager = null
        mBroccoli = null
        circleNavigator = null
        locationPermissionLauncher = null
        settingLauncher = null
        gpsLauncher = null

    }


    /**
     * 跳转到开启定位权限
     */
    private fun toOpenLocationPermission() {
        try {
            locationPermissionLauncher?.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 跳转到GPS设置页面
     */
    private fun toOpenLocationGPS() {
        try {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            gpsLauncher?.launch(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        ToastUtils.show("请打开GPS")
    }

    /**
     *  检查是否出现去开启定位提示
     * @return Boolean 是否出现
     */
    private fun isLocationStatusShow(): Boolean {
        if (isHaveLocation && selectCityPosition == 0 && MMKVUtils.getBool(WeatherKeys.KEY_WEATHER_SET_MAIN_GUIDE)
            && NetworkUtil.isNetworkAvailable(mActivity)
        ) {
            var isShow = false
            //有权限，但未打开GPS的情况
            if (ActivityCompat.checkSelfPermission(
                    mActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                if (locationManager != null) {
                    locationManager?.let {
                        if (!it.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            //没有打开CPS
                            locationFailType = 2
                            isShow = true
                        }
                    }
                }
            } else {
                //没有权限
                locationFailType = 1
                isShow = true
            }
            return isShow
        } else {
            return false
        }
    }

    inner class ProxyClick {
        fun toCityManger() {

        }

        fun toOpenLocation() {
            if (!isLocationStatusShow()) return
            if (locationFailType == 1) {
                toOpenLocationPermission()
            } else {
                toOpenLocationGPS()
            }
        }

    }
}