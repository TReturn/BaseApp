package com.example.lib_base.utils.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.util.*

/**
 * @CreateDate : 2021/8/17 0:33
 * @Author : 青柠
 * @Description : 原生的定位实现
 */
class ProtistLocationUtils(private val context: Context) {
    private lateinit var locationManager: LocationManager

    /**
     * 获取权限，并检查有无开户GPS
     */
    private fun initLocationManager() {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // 转到手机设置界面，用户设置GPS
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
        providers
    }

    /**
     * 获取可定位的方式
     */
    private lateinit var myLocationListener: MyLocationListener
    private lateinit var bestProvider: String

    //获取定位方式
    @get:SuppressLint("WrongConstant")
    private val providers: Unit
        // 查询精度：高，Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精确
        // 是否查询海拨：否
        // 是否查询方位角 : 否
        // 设置是否要求速度
        // 电量要求：低
        //获取最佳定位
        private get() {
            //获取定位方式
            val providers = locationManager.getProviders(true)
            for (s in providers) {
                Log.e(TAG, s)
            }
            val criteria = Criteria()
            // 查询精度：高，Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精确
            criteria.accuracy = Criteria.ACCURACY_FINE
            // 是否查询海拨：否
            criteria.isAltitudeRequired = true
            // 是否查询方位角 : 否
            criteria.isBearingRequired = false
            // 设置是否要求速度
            criteria.isSpeedRequired = false
            // 电量要求：低
            criteria.powerRequirement = Criteria.ACCURACY_LOW
            bestProvider = locationManager.getBestProvider(criteria, false).toString() //获取最佳定位
            myLocationListener = MyLocationListener()
        }

    fun startLocation() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        Log.e(TAG, "startLocation: ")
        myLocationListener.let {
            locationManager.requestLocationUpdates(bestProvider, 0, 0f,
                it
            )
        }
    }

    fun stopLocation() {
        Log.e(TAG, "stopLocation: ")
        myLocationListener.let { locationManager.removeUpdates(it) }
    }

    private inner class MyLocationListener : LocationListener {
        override fun onLocationChanged(location: Location) {
            //定位时调用
            Log.e(TAG, "onLocationChanged")
            var addresses: List<Address> = ArrayList()
            //经纬度转城市
            val geocoder = Geocoder(context)
            try {
                addresses = geocoder.getFromLocation(location.latitude, location.longitude, 10)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            for (address in addresses) {
                //国家  CN
                Log.e(TAG, address.countryCode)
                //国家
                Log.e(TAG, address.countryName)
                //省，市，地址
                Log.e(TAG, address.adminArea)
                Log.e(TAG, address.locality)
                Log.e(TAG, address.featureName)

                //经纬度
                Log.e(TAG, address.latitude.toString())
                Log.e(TAG, address.longitude.toString())
                //                Log.e(TAG,address.getAddressLine());
            }
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            //定位状态改变
            Log.e(TAG, "onStatusChanged")
        }

        override fun onProviderEnabled(provider: String) {
            //定位开启
            Log.e(TAG, "onProviderEnabled")
        }

        override fun onProviderDisabled(provider: String) {
            //定位关闭
            Log.e(TAG, "onProviderDisabled")
        }
    }

    companion object {
        private const val TAG = "GPS-Info"
    }

    init {
        initLocationManager()
    }
}