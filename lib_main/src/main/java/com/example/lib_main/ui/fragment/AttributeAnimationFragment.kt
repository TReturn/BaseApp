package com.example.lib_main.ui.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import com.example.lib_base.base.BaseFragment
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentAttributeAnimationBinding
import com.example.lib_main.viewmodel.AttributeAnimationViewModel
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import me.hgj.jetpackmvvm.ext.nav

/**
 * @CreateDate: 2023/8/24 19:54
 * @Author: 青柠
 * @Description: 属性动画
 */
class AttributeAnimationFragment : BaseFragment<AttributeAnimationViewModel, FragmentAttributeAnimationBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
        setTranslucent(mDatabind.flTranslucent)

        mDatabind.include.titleBar.title = getString(R.string.main_type_attribute_animation)
        mDatabind.include.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                nav().navigateUp()
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