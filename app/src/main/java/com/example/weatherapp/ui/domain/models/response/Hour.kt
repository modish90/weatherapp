package com.example.weatherapp.ui.domain.models.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Hour(
    val time: String? = null,
    val icon: String? = null,
    val temperature: String? = null,
)
