package com.example.weatherapp.data.repository

import com.example.weatherapp.data.sampleForecastResponse
import com.example.weatherapp.ui.data.repository.WeatherForecastRepositoryImpl
import com.example.weatherapp.ui.data.services.WeatherService
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import com.example.weatherapp.ui.data.dto.toWeather
import com.example.weatherapp.ui.domain.models.response.ResponseWrapper
import com.example.weatherapp.ui.domain.models.response.Weather
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DefaultWeatherRepositoryTest {
    private lateinit var repository: WeatherForecastRepositoryImpl

    var weatherApi = mockk<WeatherService>()

    @Before
    fun setup() {
        repository = WeatherForecastRepositoryImpl(weatherApi)
    }

    @Test
    fun `when getWeatherForecast is called, it should show the success call or state`() =
        runTest {
            coEvery {
                weatherApi.getWeatherForecast(
                    any(),
                    any(),
                    any()
                )
            } returns sampleForecastResponse

            val results = mutableListOf<Weather>()
            val response = repository.getWeatherDetails(city = "Munich")
            when (response) {
                is ResponseWrapper.Success -> {
                    response.data?.let {
                        results.add(it)
                    }
                }

                else -> {}
            }
            assertEquals(sampleForecastResponse.body()?.toWeather(), results[0])
        }

    @Test
    fun `when getWeatherForecast is called, it should show the error call or state`() =
        runTest {
            coEvery {
                weatherApi.getWeatherForecast(
                    any(),
                    any(),
                    any()
                )
            } returns sampleForecastResponse

            val results = mutableListOf<Weather>()
            val response = repository.getWeatherDetails(city = "Munich")
            when (response) {
                is ResponseWrapper.Error -> {

                }
                else -> {}
            }
        }
}