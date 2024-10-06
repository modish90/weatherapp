package com.example.weatherapp.ui.domain.models.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather(
    val temperature: Int? = null,
    val date: String? = null,
    val wind: Int? = null,
    val humidity: Int? = null,
    val feelsLike: Int? = null,
    val condition: Condition? = null,
    val uv: Int? = null,
    val name: String? = null,
    val forecasts: List<Forecast>? = null
)
