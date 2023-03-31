package com.example.lib_main.ui.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.constant.RouterUrls
import com.example.lib_base.utils.ui.UiUtils
import com.example.lib_main.R
import com.example.lib_main.databinding.ActivityAttributeAnimationBinding
import com.example.lib_main.viewmodel.AttributeAnimationViewModel
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar

/**
 * @CreateDate: 2023/3/30 18:16
 * @Author: 青柠
 * @Description: 属性动画
 */
@Route(path = RouterUrls.ROUTER_URL_ATT_ANIMATION)
class AttributeAnimationActivity :
    BaseActivity<AttributeAnimationViewModel, ActivityAttributeAnimationBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()

        mDatabind.include.titleBar.title = UiUtils.getString(R.string.main_type_attribute_animation)
        mDatabind.include.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                finish()
            }
        })
    }

    inner class ProxyClick {
        fun toAlpha() {
            //透明度：alpha
            ObjectAnimator.ofFloat(mDatabind.ivLoading, "alpha", 1F, 0F, 1F).run {
                duration = 2000
                start()
            }
        }

        fun toTranslation() {
            //平移：translationX，translationY
            val mIvLoadingX = mDatabind.ivLoading.translationX
            ObjectAnimator.ofFloat(mDatabind.ivLoading, "translationX", mIvLoadingX, -200F, mIvLoadingX)
                .run {
                    duration = 1000
                    start()
                }
        }

        fun toRotation() {
            //旋转：rotation，rotationX,rotationY
            ObjectAnimator.ofFloat(mDatabind.ivLoading, "rotation", 0F, 360F).run {
                duration = 1000
                //循环次数：ValueAnimator.INFINITE无限循环，可传入Int值
                repeatCount = 1
                start()
            }
        }

        fun toScale() {
            //缩放：scaleX，scaleY
            ObjectAnimator.ofFloat(mDatabind.ivLoading, "scaleX", 1F, 3F, 1F).run {
                duration = 2000
                start()
            }
        }

    }

}