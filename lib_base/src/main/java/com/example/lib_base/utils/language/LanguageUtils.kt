package com.example.lib_base.utils.language

import android.text.TextUtils
import com.example.lib_base.constant.UserKeys
import com.example.lib_base.utils.data.MMKVUtils
import java.util.*

/**
 * @CreateDate : 2021/8/6
 * @Author : 青柠
 * @Description : APP多语言相关
 */
object LanguageUtils {

    /**
     * 获取语言
     * @return 返回“zh”之类国家码
     */
    private fun getLanguage(): String {
        return if (TextUtils.isEmpty(MMKVUtils.getString(UserKeys.LANGUAGE))) {
            //默认语言
            Locale.getDefault().language
        } else {
            MMKVUtils.getString(UserKeys.LANGUAGE)
        }
    }

    /**
     * 当前语言是否为中国
     * @return Boolean
     */
    fun isChinese(): Boolean {
        return getLanguage() == "zh"
    }
}