package com.example.lib_base.utils.data

import kotlin.random.Random

/**
 * @CreateDate : 2021/8/27 15:05
 * @Author : 青柠
 * @Description : 随机数工具类
 */
object RandomUtils {
    /**
     * 生成0-x的随机数
     * @param until x
     * @return 随机数
     */
    fun getRandomInt(until: Int): Int {
        return Random.nextInt(until)
    }
}