package com.example.weatherapp.ui.data.services

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.ui.data.response.WeatherAPIResponse
import com.example.weatherapp.ui.data.utils.WeatherConstants.DEFAULT_WEATHER_DESTINATION
import com.example.weatherapp.ui.data.utils.WeatherConstants.NUMBER_OF_DAYS
import retrofit2.Response
import retrofit2.http.*

interface WeatherService : BaseService{

    @GET("forecast.json")
    suspend fun getWeatherForecast(
        @Query("key") key: String = BuildConfig.WEATHER_API_KEY,
        @Query("q") city: String = DEFAULT_WEATHER_DESTINATION,
        @Query("days") days: Int = NUMBER_OF_DAYS,
    ): Response<WeatherAPIResponse>
}