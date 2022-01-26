package com.example.lib_base.utils.calculate

import java.util.regex.Pattern

/**
 * @CreateDate : 2021/8/6
 * @Author : 青柠
 * @Description : 正则表达式校验
 */
object RegexUtils {

    /**
     * 中国手机号校验
     * @param word 密码
     * @return Boolean
     */
    fun isChinaPhone(phone: String): Boolean {
        val regex = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}\$"
        val pattern = Pattern.compile(regex)
        return pattern.matcher(phone).matches()
    }

    /**
     * 密码校验，必须包含数字和字母，8-20位
     * @param word 密码
     * @return Boolean
     */
    fun isPassWord(word: String): Boolean {
        val regex = "^(?![0-9]+\$)(?![a-zA-Z]+\$)[0-9A-Za-z]{8,20}\$"
        val pattern = Pattern.compile(regex)
        return pattern.matcher(word).matches()
    }

    /**
     * 邮箱校验
     * @param email 邮箱地址
     * @return Boolean
     */
    fun isEmail(email: String): Boolean {
        val regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$"
        val pattern = Pattern.compile(regex)
        return pattern.matcher(email).matches()
    }

    /**
     * 网址校验
     * @param url 网址
     * @return Boolean
     */
    fun isUrl(url: String): Boolean {
        val regex = "^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-Z0-9\\.&amp;%\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&amp;%\$#\\=~_\\-@]*)*\$"
        val pattern = Pattern.compile(regex)
        return pattern.matcher(url).matches()
    }
}