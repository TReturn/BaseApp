package com.wood.lib_base.utils.search

import com.example.lib_base.utils.search.SearchSort
import java.util.*


/**
 * @CreateDate : 2021/8/6
 * @Author : 青柠
 * @Description : 实现本地搜索功能
 */
object SearchLikeBy {

    // 默认使用名称去排序
    private  var flag = true

    /**
     * Map集合模糊匹配
     *
     * @param map     map集合
     * @param keyLike 模糊key
     * @return {a1=1,a1899=1899}
     */
    fun getLikeByMap(map: Map<String, String>, keyLike: String): Map<String, String> {
        /** 返回的数据map  */
        val m: MutableMap<String, String> = LinkedHashMap()

        /** 用于排序数据集合  */
        val l: MutableList<String> = LinkedList()

        // 循环匹配
        for ((key) in map) {
            val integer = map[key].toString() + ""
            if (key == keyLike) { // 判断名称完全相等
                val a = "$key&$integer"
                l.add(0, a) // 放在第一位
                continue
            }
            if (integer == keyLike) { // 判断ID完全相等
                val a = "$key&$integer"
                l.add(0, a) // 放在第一位
                flag = false
                continue
            }
            if (key.indexOf(keyLike) > -1) { // 判断key值
                val a = "$key&$integer"
                l.add(a)
            }
            if (integer.indexOf(keyLike) > -1) { // 判断value
                val a = "$key&$integer"
                l.add(a)
            }
        }
        // 将数据长短进行排序

        // 将第一个数据取出，不排序
        // 判断第一个是否为完全匹配到的
        if (null != l) {
            if (l.size > 0) {
                var one: String? = l[0]
                if (one!!.contains("&")) {
                    val split = one.split("&").toTypedArray()
                    val name = split[0]
                    val id = split[1]
                    if (name == keyLike || id == keyLike) {
                        l.removeAt(0)
                    } else {
                        one = null
                    }
                }
                val test: MutableList<String> = LinkedList()
                if (flag) { // 名称
                    for (string in l) {
                        val split = string.split("&").toTypedArray()
                        val name = split[0]
                        test.add(name)
                    }
                } else { // ID
                    for (string in l) {
                        val split = string.split("&").toTypedArray()
                        val id = split[1]
                        test.add(id)
                    }
                }
                val sort: List<String> = SearchSort.Sort(test)
                var list: MutableList<String> = LinkedList()
                if (flag) {
                    for (i in sort.indices) {
                        val string = sort[i]
                        val integer = map[string]
                        list.add(i, "$string&$integer")
                    }
                } else {
                    for (i in sort.indices) {
                        val string = sort[i]
                        for ((key) in map) {
                            val integer = map[key].toString() + ""
                            if (integer == string) {
                                list.add(i, "$key&$integer")
                            }
                        }
                    }
                }
                if (null != one) {
                    list.add(0, one)
                }
                for (res in list) {
                    val split = res.split("&").toTypedArray()
                    val name = split[0]
                    val id = split[1]
                    m[name] = id.trim { it <= ' ' }
                }
            }
        }
        return m
    }


}