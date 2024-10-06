package com.example.weatherapp.ui.domain.models.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Condition(
    val icon: String? = null,
    val text: String? = null
)