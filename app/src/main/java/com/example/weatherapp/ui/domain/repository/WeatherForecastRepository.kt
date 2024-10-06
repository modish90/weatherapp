package com.example.weatherapp.ui.domain.repository

import com.example.weatherapp.ui.domain.models.response.ResponseWrapper
import com.example.weatherapp.ui.domain.models.response.Weather

interface WeatherForecastRepository {
    suspend fun getWeatherDetails(city: String): ResponseWrapper<Weather>
}