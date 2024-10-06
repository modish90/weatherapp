package com.example.weatherapp.data.repository

import com.example.weatherapp.ui.domain.models.response.Condition
import com.example.weatherapp.ui.domain.models.response.Forecast
import com.example.weatherapp.ui.domain.models.response.Hour
import com.example.weatherapp.ui.domain.models.response.ResponseWrapper
import com.example.weatherapp.ui.domain.models.response.Weather
import com.example.weatherapp.ui.domain.repository.WeatherForecastRepository

object FakeWeatherRepository : WeatherForecastRepository {

    override suspend fun getWeatherDetails(city: String): ResponseWrapper<Weather> {
        return ResponseWrapper.Success(data = fakeWeather)
    }
}

val fakeWeather = Weather(
    temperature = 20,
    date = "2023-10-15",
    wind = 10,
    humidity = 60,
    feelsLike = 25,
    condition = Condition("sunny-icon", "Sunny"),
    uv = 5,
    name = "San Francisco",
    forecasts = listOf(
        Forecast(
            date = "2023-10-15",
            maxTemp = "25",
            minTemp = "15",
            sunrise = "06:30 AM",
            sunset = "06:30 PM",
            icon = "sunny-icon",
            hour = listOf(
                Hour("08:00 AM", "sunny-icon", "Sunny"),
                Hour("09:00 AM", "sunny-icon", "Sunny"),
                Hour("10:00 AM", "sunny-icon", "Sunny")
            )
        )
    )
)
