package com.example.weatherapp.ui.viewstate

import com.example.weatherapp.ui.domain.models.response.ErrorWrapper
import com.example.weatherapp.ui.domain.models.response.Weather

sealed class WeatherState {

    class Success(
        val weather: Weather? = null,
    ) : WeatherState()

    object LOADING : WeatherState()

    class Error(val errorWrapper: ErrorWrapper) : WeatherState()
}