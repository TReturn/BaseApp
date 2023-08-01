package com.example.lib_base.utils.data

import android.os.Parcelable
import com.tencent.mmkv.MMKV

/**
 * @CreateDate: 2021/1/4
 * @Author: 青柠
 * @Description: MMKV封装
 */

object MMKVUtils {

    //加密MMKV，不使用明文存储
    var cryptKey = "My-Base-Key"
    private var kv = MMKV.mmkvWithID("MyID", MMKV.MULTI_PROCESS_MODE, cryptKey)


    /**
     * String存取
     */
    fun put(key: String, value: String) {
        kv.encode(key, value)
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return kv.decodeString(key, defaultValue).toString()
    }

    /**
     * StringSet存取
     */
    fun put(key: String, value: Set<String>) {
        kv.encode(key, value)
    }

    fun getStringSet(key: String): MutableSet<String> {
        return kv.decodeStringSet(key) as MutableSet<String>
    }

    /**
     * int存取
     */
    fun put(key: String, value: Int) {
        kv?.encode(key, value)
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return kv.decodeInt(key, defaultValue)
    }

    /**
     * long存取
     */
    fun put(key: String, value: Long) {
        kv?.encode(key, value)
    }

    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return kv.decodeLong(key, defaultValue)
    }

    /**
     * boolean存取
     */
    fun put(key: String, value: Boolean) {
        kv.encode(key, value)
    }

    fun getBool(key: String, defaultValue: Boolean = false): Boolean {
        return kv.decodeBool(key, defaultValue)
    }

    /**
     * double存取
     */
    fun put(key: String, value: Double) {
        kv.encode(key, value)
    }

    fun getDouble(key: String, defaultValue: Double = 0.0): Double {
        return kv.decodeDouble(key, defaultValue)
    }

    /**
     * Parcelable存取
     */
    fun put(key: String, value: Parcelable?) {
        kv.encode(key, value)
    }

    fun <T : Parcelable?> getParcelable(
        key: String,
        mClass: Class<T>,
        defaultValue: T? = null
    ): T? {
        return kv.decodeParcelable(key, mClass, defaultValue)
    }

}