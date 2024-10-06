package com.example.weatherapp.ui.data.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataCondition(
    val icon: String?= null,
    val text: String?= null
)