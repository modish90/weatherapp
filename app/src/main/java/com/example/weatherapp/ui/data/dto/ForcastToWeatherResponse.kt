package com.example.weatherapp.ui.data.dto

import com.example.weatherapp.ui.data.response.Forecastday
import com.example.weatherapp.ui.data.response.NetworkHour
import com.example.weatherapp.ui.data.response.WeatherAPIResponse
import com.example.weatherapp.ui.domain.models.response.Condition
import com.example.weatherapp.ui.domain.models.response.Forecast
import com.example.weatherapp.ui.domain.models.response.Hour
import com.example.weatherapp.ui.domain.models.response.Weather

fun WeatherAPIResponse.toWeather(): Weather = Weather(
    temperature = current?.temp_c?.toInt(),
    date = forecast?.forecastday?.get(0)?.date,
    wind = current?.wind_kph?.toInt(),
    humidity = current?.humidity,
    feelsLike = current?.feelslike_c?.toInt(),
    condition = Condition(current?.condition?.icon, current?.condition?.text),
    uv = current?.uv?.toInt(),
    forecasts = forecast?.forecastday?.map {
        it.toWeatherForecast()
    },
    name = location?.name,
)

fun Forecastday.toWeatherForecast(): Forecast = Forecast(
    date = date,
    maxTemp = day?.maxtemp_c?.toInt().toString(),
    minTemp = day?.mintemp_c?.toInt().toString(),
    sunrise = astro?.sunrise,
    sunset = astro?.sunset,
    icon = day?.condition?.icon,
    hour = hour?.map { networkHour ->
        networkHour.toHour()
    }
)

fun NetworkHour.toHour(): Hour = Hour(
    time = time,
    icon = condition?.icon,
    temperature = temp_c?.toInt().toString(),
)