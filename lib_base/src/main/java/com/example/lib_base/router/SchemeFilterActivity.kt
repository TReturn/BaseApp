package com.example.lib_base.router

import android.app.Activity
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @CreateDate : 2020/12/31
 * @Author : 青柠
 * @Description : 新建一个Activity用于监听Scheme事件,之后直接把url传递给ARouter即可
 */
class SchemeFilterActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri = intent.data
        ARouter.getInstance().build(uri).navigation()
        finish()
    }
}