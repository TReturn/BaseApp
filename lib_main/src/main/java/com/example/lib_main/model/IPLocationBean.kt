package com.example.lib_main.model
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


/**
 * @CreateDate : 2021/8/17 1:14
 * @Author : 青柠
 * @Description :
 */
@Serializable
data class IPLocationBean(
    @SerialName("address")
    var address: String = "",
    @SerialName("content")
    var content: Content = Content(),
    @SerialName("status")
    var status: Int = 0
) {
    @Serializable
    data class Content(
        @SerialName("address")
        var address: String = "",
        @SerialName("address_detail")
        var addressDetail: AddressDetail = AddressDetail(),
        @SerialName("point")
        var point: Point = Point()
    ) {
        @Serializable
        data class AddressDetail(
            @SerialName("city")
            var city: String = "",
            @SerialName("city_code")
            var cityCode: Int = 0,
            @SerialName("district")
            var district: String = "",
            @SerialName("province")
            var province: String = "",
            @SerialName("street")
            var street: String = "",
            @SerialName("street_number")
            var streetNumber: String = ""
        )

        @Serializable
        data class Point(
            @SerialName("x")
            var x: String = "",
            @SerialName("y")
            var y: String = ""
        )
    }
}