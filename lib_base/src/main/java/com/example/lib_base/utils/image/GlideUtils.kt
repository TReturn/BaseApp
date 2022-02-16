package com.example.lib_base.utils.image

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.lib_base.R
import com.example.lib_base.utils.calculate.DensityUtils.dip2px

/**
 * @CreateDate : 2021/7/30
 * @Author : 青柠
 * @Description : Glide工具类
 * 1.带占位图的加载图片方式，适合加载网络图片: loadImage()
 * 2.直接加载，适合加载本地资源: loadImageProtist()
 * 3.加载圆角图片，解决设置CenterCroup设置圆角不生效的问题: loadRoundImage()
 * 4.Transform方式加载圆角: loadRoundImageTransform()
 * 5.加载圆形图片:加载圆形图片: loadCircleImage()
 * 6.压缩加载图片: loadRoundOverrideImage()
 * 7.预加载图片，先缓存到磁盘中: loadPreload()
 */
object GlideUtils {

    /**
     * 默认配置
     */
    private var requestOptions: RequestOptions = RequestOptions()
        //设置预加载图、错误图
        .placeholder(R.color.color_gray)
        .error(R.color.color_gray)
        .fallback(R.color.color_gray)

    /**
     * 1.带占位图的加载图片方式，适合加载网络图片
     * @param url Any
     * @param imageView ImageView
     */
    fun loadImage(context: Context?, url: Any, imageView: ImageView) {
        context?.let {
            Glide.with(context).load(url)
                .apply(requestOptions)
                .into(imageView)
        }

    }

    /**
     * 2.直接加载，适合加载本地资源
     * @param url Any
     * @param imageView ImageView
     */
    fun loadImageProtist(context: Context?, url: Any, imageView: ImageView) {
        context?.let {
            Glide.with(context).load(url)
                .into(imageView)
        }

    }

    /**
     * 3.加载圆角图片，解决设置CenterCroup设置圆角不生效的问题。
     * @param url Any
     * @param imageView ImageView
     * @param radius 圆角半径
     */
    fun loadRoundImage(context: Context?, url: Any, imageView: ImageView, radius: Float) {
        context?.let {
            //设置图片圆角角度
            val roundedCorners = RoundedCorners(dip2px(radius))
            val options = RequestOptions.bitmapTransform(roundedCorners)
                .placeholder(R.color.color_gray)
                .error(R.color.color_gray)
                .fallback(R.color.color_gray)

            Glide.with(context).load(url)
                .apply(options)
                .into(imageView)
        }

    }

    /**
     * 4.Transform方式加载圆角
     * @param url Any
     * @param imageView ImageView
     * @param radius 圆角半径
     */
    fun loadRoundImageTransform(
        context: Context?,
        url: Any,
        imageView: ImageView,
        radius: Int
    ) {
        context?.let {
            Glide.with(context).load(url)
                .apply(requestOptions)
                .transform(MultiTransformation(CenterCrop(), RoundedCorners(radius)))
                .into(imageView)
        }

    }

    /**
     * 5.加载圆形图片
     * @param activity FragmentActivity?
     * @param url Any
     * @param imageView ImageView
     */
    fun loadCircleImage(
        activity: FragmentActivity?,
        url: Any,
        imageView: ImageView,
    ) {
        activity?.let {
            if (!activity.isDestroyed) {
                Glide.with(activity).load(url)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .into(imageView)
            }
        }
    }

    /**
     * 6.压缩加载图片
     * @param url Any
     * @param imageView ImageView
     * @param width Int
     * @param height Int
     */
    @SuppressLint("CheckResult")
    fun loadRoundOverrideImage(
        context: Context?,
        url: Any,
        imageView: ImageView,
        width: Int,
        height: Int
    ) {
        context?.let {
            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
            requestOptions.override(width, height)
            Glide.with(context).load(url)
                .apply(requestOptions)
                .into(imageView)
        }

    }

    /**
     * 7.预加载图片，先缓存到磁盘中
     * @param context Context?
     * @param url Any
     * @param width Int
     * @param height Int
     */
    fun loadPreload(context: Context?, url: Any, width: Int, height: Int) {
        context?.let {
            Glide.with(it).load(url)
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .preload(width, height)
        }
    }
}