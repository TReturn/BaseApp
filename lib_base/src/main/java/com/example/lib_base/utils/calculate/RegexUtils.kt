package com.example.lib_base.utils.calculate

import java.util.regex.Pattern

/**
 * @CreateDate : 2021/8/6
 * @Author : 青柠
 * @Description : 正则表达式校验
 */
object RegexUtils {
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
     * @param url 邮箱地址
     * @return Boolean
     */
    fun isEmail(url: String): Boolean {
        val regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$"
        val pattern = Pattern.compile(regex)
        return pattern.matcher(url).matches()
    }
}