package com.example.lib_base.ext

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.Rotate
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.lib_base.R

/**
 * @CreateDate: 2023/9/2 13:24
 * @Author: 青柠
 * @Description: Glide的扩展，添加DataBinding注解，方便在xml中调用
 */

//默认加载图
val defaultPlaceHolder = R.drawable.shape_glide_loading.toDrawable()
val defaultError = R.drawable.shape_glide_loading.toDrawable()
val defaultFallback = R.drawable.shape_glide_loading.toDrawable()

@BindingAdapter(
    value = ["imageUrl", "placeholder", "error", "fallback", "loadWidth", "loadHeight", "imageRadius"],
    requireAll = false
)

//提供加载圆角图片能力
fun setImageUrl(
    view: ImageView,
    source: Any? = null,
    placeholder: Drawable? = null,
    error: Drawable? = null,
    fallback: Drawable? = null,
    width: Int? = -1,
    height: Int? = -1,
    imageRadius: Int? = -1
) {
    // 计算位图尺寸，如果位图尺寸固定，加载固定大小尺寸的图片，如果位图未设置尺寸，那就加载原图，Glide加载原图时，override参数设置 -1 即可。
    val widthSize = (if ((width ?: 0) > 0) width else view.width) ?: -1
    val heightSize = (if ((height ?: 0) > 0) height else view.height) ?: -1

    //圆角转换
    val transform: MultiTransformation<Bitmap> = if (imageRadius != null && imageRadius > 0) {
        MultiTransformation(CenterCrop(), RoundedCorners(imageRadius))
    } else {
        MultiTransformation(CenterCrop(), Rotate(0))
    }

    val options = RequestOptions.bitmapTransform(transform)
        .placeholder(placeholder ?: defaultPlaceHolder)
        .error(error ?: defaultError)
        .fallback(fallback ?: defaultFallback)

    Glide.with(view.context)
        .asDrawable()
        .load(source)
        .override(widthSize, heightSize)
        .apply(options)
        .into(view)
}

@BindingAdapter(
    value = ["circleImageUrl", "placeholder", "error", "fallback", "loadWidth", "loadHeight"],
    requireAll = false
)

//提供加载圆形状图片能力
fun setCircleImageUrl(
    view: ImageView,
    source: Any? = null,
    placeholder: Drawable? = null,
    error: Drawable? = null,
    fallback: Drawable? = null,
    width: Int? = -1,
    height: Int? = -1
) {
    // 计算位图尺寸，如果位图尺寸固定，加载固定大小尺寸的图片，如果位图未设置尺寸，那就加载原图，Glide加载原图时，override参数设置 -1 即可。
    val widthSize = (if ((width ?: 0) > 0) width else view.width) ?: -1
    val heightSize = (if ((height ?: 0) > 0) height else view.height) ?: -1

    val options = RequestOptions.bitmapTransform(CircleCrop())
        .placeholder(placeholder ?: defaultPlaceHolder)
        .error(error ?: defaultError)
        .fallback(fallback ?: defaultFallback)

    Glide.with(view.context)
        .asDrawable()
        .load(source)
        .override(widthSize, heightSize)
        .apply(options)
        .into(view)
}

