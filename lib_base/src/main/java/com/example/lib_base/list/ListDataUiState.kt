package com.example.lib_base.list

/**
 * @CreateDate : 2021/8/8 14:44
 * @Author : 青柠
 * @Description :
 */
/**
 * 作者　: hegaojian
 * 时间　: 2020/3/3
 * 描述　: 列表数据状态类
 */
data class ListDataUiState<T>(
    //是否请求成功
    val isSuccess: Boolean,
    //是否为刷新
    val isRefresh: Boolean = false,
    //是第一页且没有数据
    val isFirstEmpty:Boolean = false,
    //列表数据
    val listData: List<T> = arrayListOf()
)