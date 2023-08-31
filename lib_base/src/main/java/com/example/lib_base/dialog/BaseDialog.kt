package com.example.lib_base.dialog

import android.content.Context
import com.lxj.xpopup.XPopup

/**
 * @CreateDate: 2023/8/31 15:53
 * @Author: 青柠
 * @Description:
 */
object BaseDialog {
    /**
     * 获取一个默认的Dialog
     * @param context Context
     * @param isDismissOnTouch Boolean
     * @return XPopup.Builder
     */
    fun get(context: Context, isDismissOnTouch: Boolean = true): XPopup.Builder {
        return XPopup.Builder(context)
            .isDestroyOnDismiss(true)
            .dismissOnTouchOutside(isDismissOnTouch)
            .dismissOnBackPressed(isDismissOnTouch)

    }
}