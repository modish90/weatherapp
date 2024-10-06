package com.example.weatherapp.ui.data.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkHour(
    val condition: DataCondition?= null,
    val temp_c: Double?= null,
    val time: String?= null
)