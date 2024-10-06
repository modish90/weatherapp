package com.example.weatherapp.ui.data.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataForecast(
    val forecastday: List<Forecastday>?= null
)