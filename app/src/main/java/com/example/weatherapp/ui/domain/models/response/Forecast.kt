package com.example.weatherapp.ui.domain.models.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Forecast(
    val date: String? = null,
    val maxTemp: String? = null,
    val minTemp: String? = null,
    val sunrise: String? = null,
    val sunset: String? = null,
    val icon: String? = null,
    val hour: List<Hour>? = null
)
