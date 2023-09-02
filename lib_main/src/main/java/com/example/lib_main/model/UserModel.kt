package com.example.lib_main.model

/**
 * @CreateDate: 2023/2/27 11:02
 * @Author: 青柠
 * @Description:
 */
data class UserModel(
    var id: Int = 0,
    var title: String = "",
    var subTitle: String = "",
    var imageRes: Int = 0,
    var isEnd: Boolean = false
)