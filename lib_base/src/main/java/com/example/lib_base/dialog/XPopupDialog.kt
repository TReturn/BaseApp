package com.example.lib_base.dialog

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lxj.xpopup.core.BottomPopupView
import com.lxj.xpopup.core.BubbleAttachPopupView
import com.lxj.xpopup.core.CenterPopupView
import com.lxj.xpopup.impl.FullScreenPopupView

/**
 * @CreateDate: 2024/3/14 10:30
 * @Author: 青柠
 * @Description:
 */
abstract class BaseCenterDialog<V : ViewDataBinding>(
    context: Context,
    @LayoutRes val layoutRes: Int
) :
    CenterPopupView(context) {

    var mDatabind: V? = null
    override fun getImplLayoutId(): Int {
        return layoutRes
    }

    override fun onCreate() {
        super.onCreate()
        mDatabind = DataBindingUtil.bind(popupImplView)
    }
}

abstract class BaseFullDialog<V : ViewDataBinding>(
    context: Context,
    @LayoutRes val layoutRes: Int
) :
    FullScreenPopupView(context) {

    var mDatabind: V? = null
    override fun getImplLayoutId(): Int {
        return layoutRes
    }

    override fun onCreate() {
        super.onCreate()
        mDatabind = DataBindingUtil.bind(popupImplView)
    }
}

abstract class BaseBottomDialog<V : ViewDataBinding>(
    context: Context,
    @LayoutRes val layoutRes: Int
) :
    BottomPopupView(context) {

    var mDatabind: V? = null
    override fun getImplLayoutId(): Int {
        return layoutRes
    }

    override fun onCreate() {
        super.onCreate()
        mDatabind = DataBindingUtil.bind(popupImplView)
    }
}

abstract class BaseAttachDialog<V : ViewDataBinding>(
    context: Context,
    @LayoutRes val layoutRes: Int
) :
    BubbleAttachPopupView(context) {

    var mDatabind: V? = null
    override fun getImplLayoutId(): Int {
        return layoutRes
    }

    override fun onCreate() {
        super.onCreate()
        mDatabind = DataBindingUtil.bind(popupImplView)
    }
}

