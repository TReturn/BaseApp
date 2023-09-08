package com.example.lib_base.dialog

import android.annotation.SuppressLint
import android.app.Activity
import androidx.fragment.app.FragmentActivity
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.core.CenterPopupView
import com.lxj.xpopup.interfaces.OnConfirmListener

/**
 * @CreateDate : 2021/8/13
 * @Author : 青柠
 * @Description : 显示确认和取消对话框
 */
@SuppressLint("ViewConstructor")
class BaseConfirmDialog(
    context: FragmentActivity,
    private val title: String,
    private val content: String,
    private val confirmCallback: () -> Unit
) :
    CenterPopupView(context) {

    override fun show(): BasePopupView {
        return XPopup.Builder(context).asConfirm(
            title, content
        ) { confirmCallback.invoke() }
            .show()
    }
}