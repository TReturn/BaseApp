package com.example.lib_main.manager

/**
 * @CreateDate: 2023/2/22 18:21
 * @Author: 青柠
 * @Description: 弹窗按钮回调
 */
interface DialogListener {
    //确定按钮
    fun onConfirm(type: String = "") {}

    //取消按钮
    fun onCancel() {}


}