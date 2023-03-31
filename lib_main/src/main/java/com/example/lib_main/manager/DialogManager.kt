package com.example.lib_main.manager

import android.app.Activity
import com.example.lib_base.BaseApplication
import com.example.lib_main.ui.dialog.SelectLanguageDialog
import com.lxj.xpopup.XPopup

/**
 * @CreateDate: 2023/3/31 15:43
 * @Author: 青柠
 * @Description:
 */
object DialogManager {

    //弹窗按钮回调
    private var lister: DialogListener? = null

    /**
     * 设置回调
     * @param listener DialogListener?
     */
    fun setListener(listener: DialogListener?) {
        lister = listener
    }

    /**
     * 选择语音弹窗
     * @return DialogManager
     */
    fun showSelectLanguageDialog(context:Activity): DialogManager {
        XPopup.Builder(context).asCustom(SelectLanguageDialog(context) {
            lister?.onConfirm()
        }).show()

        return this
    }

}