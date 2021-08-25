package com.example.lib_base.utils.data

import com.tencent.mmkv.MMKV

/**
 * @CreateDate: 2020/12/30
 * @Author: 青柠
 * @Description: MMKV封装
 */

object MMKVUtils {

    //加密MMKV，不使用明文存储
    var cryptKey = "My-Base-Key"
    private var kv = MMKV.mmkvWithID("MyID", MMKV.SINGLE_PROCESS_MODE, cryptKey)


    /**
     * String存取
     */
    fun set(key: String, value: String) {
        kv.encode(key, value)
    }

    fun getString(key: String): String {
        return kv.decodeString(key, "").toString()
    }

    /**
     * StringSet存取
     */
    fun set(key: String, value: Set<String>) {
        kv.encode(key, value)
    }

    fun getStringSet(key: String): MutableSet<String> {
        return kv.decodeStringSet(key) as MutableSet<String>
    }

    /**
     * int存取
     */
    fun set(key: String, value: Int) {
        kv?.encode(key, value)
    }

    fun getInt(key: String): Int {
        return kv.decodeInt(key, 0)
    }

    /**
     * boolean存取
     */
    fun set(key: String, value: Boolean) {
        kv.encode(key, value)
    }

    fun getBool(key: String): Boolean {
        return kv.decodeBool(key, false)
    }

    /**
     * double存取
     */
    fun set(key: String, value: Double) {
        kv.encode(key, value)
    }

    fun getDouble(key: String): Double {
        return kv.decodeDouble(key, 0.0)
    }

}