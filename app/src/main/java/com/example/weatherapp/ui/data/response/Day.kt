package com.example.weatherapp.ui.data.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Day(
    val condition: DataCondition?= null,
    val maxtemp_c: Double?= null,
    val mintemp_c: Double?= null
)