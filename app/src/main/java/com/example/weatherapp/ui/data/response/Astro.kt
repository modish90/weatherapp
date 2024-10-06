package com.example.weatherapp.ui.data.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Astro(
    val sunrise: String?= null,
    val sunset: String?= null
)