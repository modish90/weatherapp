package com.example.weatherapp.ui.domain.models.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Error(
    val code: Int? = null,
    val message: String? = null
)