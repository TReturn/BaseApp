package com.example.lib_base.utils.image

import android.annotation.SuppressLint
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.lib_base.BaseApplication
import com.example.lib_base.R
import com.example.lib_base.utils.calculate.DensityUtils.dip2px

/**
 * @CreateDate : 2021/7/30
 * @Author : 青柠
 * @Description : Glide工具类
 */
object GlideUtils {

    private var requestOptions: RequestOptions = RequestOptions()
        .placeholder(R.drawable.ic_no_picture)
        .error(R.drawable.ic_no_picture)
        .fallback(R.drawable.ic_no_picture)
        .centerCrop()

    /**
     * 带占位图的加载图片方式，适合加载网络图片
     * @param url Any
     * @param imageView ImageView
     */
    fun loadImage(url: Any, imageView: ImageView) {
        Glide.with(BaseApplication.context).load(url)
            .apply(requestOptions)
            .into(imageView)
    }

    /**
     * 直接加载，适合加载本地资源
     * @param url Any
     * @param imageView ImageView
     */
    fun loadImageProtist(url: Any, imageView: ImageView) {
        Glide.with(BaseApplication.context).load(url)
            .into(imageView)
    }

    /**
     *加载圆角图片，解决设置CenterCroup设置圆角不生效的问题。
     * @param url Any
     * @param imageView ImageView
     * @param radius 圆角半径
     */
    fun loadRoundImage(url: Any, imageView: ImageView, radius: Float) {
        //设置图片圆角角度
        val roundedCorners = RoundedCorners(dip2px(radius))
        val options = RequestOptions.bitmapTransform(roundedCorners)

        Glide.with(BaseApplication.context).load(url)
            .apply(options)
            .into(imageView)
    }

    /**
     *Transform加载圆角
     * @param url Any
     * @param imageView ImageView
     * @param radius 圆角半径
     */
    fun loadRoundImageTransform(url: Any, imageView: ImageView, radius: Int) {
        Glide.with(BaseApplication.context).load(url)
            .apply(requestOptions)
            .transform(MultiTransformation(CenterCrop(), RoundedCorners(radius)))
            .into(imageView)
    }

    /**
     * 压缩加载图片
     * @param url Any
     * @param imageView ImageView
     * @param width Int
     * @param height Int
     */
    @SuppressLint("CheckResult")
    fun loadRoundOverrideImage(url: Any, imageView: ImageView, width: Int, height: Int) {
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        requestOptions.override(160, 90)
        Glide.with(BaseApplication.context).load(url)
            .apply(requestOptions)
            .into(imageView)
    }
}