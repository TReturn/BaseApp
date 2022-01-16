package com.example.lib_main.model
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


/**
 * @CreateDate: 2021/12/3 3:25 下午
 * @Author: 青柠
 * @Description:
 */
@Serializable
 data class PoetryTokenBean(
    @SerialName("data")
    var `data`: String = "",
    @SerialName("status")
    var status: String = ""
)