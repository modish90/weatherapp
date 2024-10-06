package com.example.weatherapp.ui.domain.usecases

import com.example.weatherapp.ui.domain.repository.WeatherForecastRepository

class GetWeatherForecastUseCase(private val weatherForecastRepository: WeatherForecastRepository)  {

    suspend operator fun invoke(cityName: String) = weatherForecastRepository.getWeatherDetails(cityName)
}