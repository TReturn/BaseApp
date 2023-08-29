package com.example.baseapp.ui.dialog

import android.content.Context
import android.content.Intent
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.example.baseapp.R
import com.example.lib_base.constant.UserKeys
import com.example.lib_base.utils.data.MMKVUtils
import com.example.lib_base.utils.ui.TextSpannableStringUtils
import com.example.lib_base.web.WebActivity
import com.hjq.shape.view.ShapeTextView
import com.lxj.xpopup.core.CenterPopupView

/**
 * @CreateDate: 2023/8/29 10:50
 * @Author: 青柠
 * @Description: 隐私协议挽留弹窗
 */
class PrivacyRetentionDialog(
    context: Context,
    private val confirmCallback: () -> Unit,
    private val cancelCallback: () -> Unit
) :
    CenterPopupView(context) {

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_privacy_retention
    }

    override fun onCreate() {
        super.onCreate()

        val tvContent = findViewById<TextView>(R.id.tvContent)

        findViewById<ShapeTextView>(R.id.tvConfirm).setOnClickListener {
            confirmCallback.invoke()
            dismiss()
        }

        findViewById<ShapeTextView>(R.id.tvCancel).setOnClickListener {
            cancelCallback.invoke()
            dismiss()
        }

        val privacyText = context.getString(R.string.privacy_retention_content)

        TextSpannableStringUtils.textColorAndClickable(tvContent, privacyText,
            5, 11,12,18, "#007AFF", object : ClickableSpan() {
                override fun onClick(p0: View) {
                    context. startActivity(Intent(context, WebActivity::class.java).apply {
                        putExtra("TITLE", context.getString(R.string.user_privacy_policy))
                        putExtra("URL", MMKVUtils.getString(UserKeys.PRIVACY_URL))
                    })
                }

                // 去除文本的下划线 不重写 默认会有
                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = false
                }

            },object : ClickableSpan() {
                override fun onClick(p0: View) {
                    context. startActivity(Intent(context, WebActivity::class.java).apply {
                        putExtra("TITLE", context.getString(R.string.user_agreement))
                        putExtra("URL", MMKVUtils.getString(UserKeys.USER_AGREEMENT_URL))
                    })
                }

                // 去除文本的下划线 不重写 默认会有
                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = false
                }

            })

    }

}