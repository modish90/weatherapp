package com.example.weatherapp.ui.data.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Current(
    val cloud: Int?= null,
    val condition: DataCondition?= null,
    val feelslike_c: Double?= null,
    val humidity: Int?= null,
    val temp_c: Double?= null,
    val uv: Double?= null,
    val wind_kph: Double?= null
)