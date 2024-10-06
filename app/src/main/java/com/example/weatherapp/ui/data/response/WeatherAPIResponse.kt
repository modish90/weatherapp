package com.example.weatherapp.ui.data.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherAPIResponse(
    val current: Current?= null,
    val forecast: DataForecast?= null,
    val location: Location?= null
)