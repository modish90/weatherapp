package com.example.weatherapp.data

import com.example.weatherapp.ui.data.response.Astro
import com.example.weatherapp.ui.data.response.Current
import com.example.weatherapp.ui.data.response.DataCondition
import com.example.weatherapp.ui.data.response.DataForecast
import com.example.weatherapp.ui.data.response.Day
import com.example.weatherapp.ui.data.response.Forecastday
import com.example.weatherapp.ui.data.response.Location
import com.example.weatherapp.ui.data.response.NetworkHour
import com.example.weatherapp.ui.data.response.WeatherAPIResponse
import retrofit2.Response

val sampleForecastResponse: Response<WeatherAPIResponse>

    get() = Response.success(WeatherAPIResponse(
        current = Current(
            cloud = 50,
            condition = DataCondition(
                icon = "sunny-icon",
                text = "Sunny"
            ),
            humidity = 60,
            uv = 5,
        ),
        forecast = DataForecast(
            forecastday = listOf(
                Forecastday(
                    astro = Astro(
                        sunrise = "06:30 AM",
                        sunset = "06:30 PM"
                    ),
                    date = "2023-10-15",
                    day = Day(
                        condition = DataCondition(
                            icon = "sunny-icon",
                            text = "Sunny"
                        ),
                    ),
                    hour = listOf(
                        NetworkHour(
                            condition = DataCondition(
                                icon = "sunny-icon",
                                text = "Sunny"
                            ),
                            time = "08:00 AM"
                        )
                    )
                )
            )
        ),
        location = Location(
            country = "US",
            lat = 37.7749,
            localtime = "2023-10-14 12:00 PM",
            lon = -122.4194,
            name = "SLJKFHALKDSF:LJAS",
            region = "California"
        )
    )
    )
