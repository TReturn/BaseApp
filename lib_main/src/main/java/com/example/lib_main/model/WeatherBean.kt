package com.example.lib_main.model
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


/**
 * @CreateDate : 2021/8/16 23:04
 * @Author : 青柠
 * @Description :
 */
@Serializable
data class WeatherBean(
    @SerialName("api_status")
    var apiStatus: String = "",
    @SerialName("api_version")
    var apiVersion: String = "",
    @SerialName("lang")
    var lang: String = "",
    @SerialName("location")
    var location: List<Double> = listOf(),
    @SerialName("result")
    var result: Result = Result(),
    @SerialName("server_time")
    var serverTime: Int = 0,
    @SerialName("status")
    var status: String = "",
    @SerialName("timezone")
    var timezone: String = "",
    @SerialName("tzshift")
    var tzshift: Int = 0,
    @SerialName("unit")
    var unit: String = ""
) {
    @Serializable
    data class Result(
        @SerialName("primary")
        var primary: Int = 0,
        @SerialName("realtime")
        var realtime: Realtime = Realtime()
    ) {
        @Serializable
        data class Realtime(
            @SerialName("air_quality")
            var airQuality: AirQuality = AirQuality(),
            @SerialName("apparent_temperature")
            var apparentTemperature: Double = 0.0,
            @SerialName("cloudrate")
            var cloudrate: Double = 0.0,
            @SerialName("dswrf")
            var dswrf: Double = 0.0,
            @SerialName("humidity")
            var humidity: Double = 0.0,
            @SerialName("life_index")
            var lifeIndex: LifeIndex = LifeIndex(),
            @SerialName("precipitation")
            var precipitation: Precipitation = Precipitation(),
            @SerialName("pressure")
            var pressure: Double = 0.0,
            @SerialName("skycon")
            var skycon: String = "",
            @SerialName("status")
            var status: String = "",
            @SerialName("temperature")
            var temperature: Double = 0.0,
            @SerialName("visibility")
            var visibility: Double = 0.0,
            @SerialName("wind")
            var wind: Wind = Wind()
        ) {
            @Serializable
            data class AirQuality(
                @SerialName("aqi")
                var aqi: Aqi = Aqi(),
                @SerialName("co")
                var co: Double = 0.0,
                @SerialName("description")
                var description: Description = Description(),
                @SerialName("no2")
                var no2: Double = 0.0,
                @SerialName("o3")
                var o3: Double = 0.0,
                @SerialName("pm10")
                var pm10: Double = 0.0,
                @SerialName("pm25")
                var pm25: Double = 0.0,
                @SerialName("so2")
                var so2: Double = 0.0
            ) {
                @Serializable
                data class Aqi(
                    @SerialName("chn")
                    var chn: Double = 0.0,
                    @SerialName("usa")
                    var usa: Double = 0.0
                )

                @Serializable
                data class Description(
                    @SerialName("chn")
                    var chn: String = "",
                    @SerialName("usa")
                    var usa: String = ""
                )
            }

            @Serializable
            data class LifeIndex(
                @SerialName("comfort")
                var comfort: Comfort = Comfort(),
                @SerialName("ultraviolet")
                var ultraviolet: Ultraviolet = Ultraviolet()
            ) {
                @Serializable
                data class Comfort(
                    @SerialName("desc")
                    var desc: String = "",
                    @SerialName("index")
                    var index: Int = 0
                )

                @Serializable
                data class Ultraviolet(
                    @SerialName("desc")
                    var desc: String = "",
                    @SerialName("index")
                    var index: Double = 0.0
                )
            }

            @Serializable
            data class Precipitation(
                @SerialName("local")
                var local: Local = Local(),
                @SerialName("nearest")
                var nearest: Nearest = Nearest()
            ) {
                @Serializable
                data class Local(
                    @SerialName("datasource")
                    var datasource: String = "",
                    @SerialName("intensity")
                    var intensity: Double = 0.0,
                    @SerialName("status")
                    var status: String = ""
                )

                @Serializable
                data class Nearest(
                    @SerialName("distance")
                    var distance: Double = 0.0,
                    @SerialName("intensity")
                    var intensity: Double = 0.0,
                    @SerialName("status")
                    var status: String = ""
                )
            }

            @Serializable
            data class Wind(
                @SerialName("direction")
                var direction: Double = 0.0,
                @SerialName("speed")
                var speed: Double = 0.0
            )
        }
    }
}