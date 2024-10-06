package com.example.weatherapp.ui.data.repository

import com.example.weatherapp.ui.data.dto.toWeather
import com.example.weatherapp.ui.data.provider.RetrofitServiceProvider
import com.example.weatherapp.ui.data.services.BaseService
import com.example.weatherapp.ui.data.services.WeatherService
import com.example.weatherapp.ui.domain.models.response.ResponseWrapper
import com.example.weatherapp.ui.domain.models.response.Weather
import com.example.weatherapp.ui.domain.repository.WeatherForecastRepository

class WeatherForecastRepositoryImpl(val service: BaseService) : BaseRepository(), WeatherForecastRepository {

    override suspend fun getWeatherDetails(city: String): ResponseWrapper<Weather> {
        val apiResponse = safeApiCall(
            call = {
                service as WeatherService
                service.getWeatherForecast(city = city)
            }
        )
        return when (apiResponse) {
            is ResponseWrapper.Success -> {
                apiResponse.data?.let {
                    ResponseWrapper.Success(it.toWeather())
                } ?: ResponseWrapper.Error(genericErrorMessage())
            }
            is ResponseWrapper.Error -> ResponseWrapper.Error(apiResponse.errorWrapper, httpCode = apiResponse.httpCode)
        }
    }
}