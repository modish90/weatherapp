package com.example.weatherapp.ui.data.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Forecastday(
    val astro: Astro?= null,
    val date: String?= null,
    val day: Day?= null,
    val hour: List<NetworkHour>? = null
)