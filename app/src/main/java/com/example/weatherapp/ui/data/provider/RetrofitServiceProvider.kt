package com.example.weatherapp.ui.data.provider

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.network_layer.NetworkClientFactory
import com.example.weatherapp.network_layer.NetworkClientType
import com.example.weatherapp.network_layer.RetrofitClient
import com.example.weatherapp.ui.data.services.WeatherService

object RetrofitServiceProvider {

    private val networkClient =
        NetworkClientFactory.getNetworkClient(
            NetworkClientType.RETROFIT_CLIENT,
            if (BuildConfig.DEBUG) NetworkClientFactory.WmLogLevel.BODY else NetworkClientFactory.WmLogLevel.NONE
        )

    private fun createRetrofitService(retroFitServiceType: RetroFitServiceType): Any {
        networkClient as RetrofitClient
        return when (retroFitServiceType) {
            RetroFitServiceType.WEATHER_SERVICE -> {
                networkClient.getNetworkClient().create(
                    WeatherService::class.java
                )
            }
            else ->
                throw RuntimeException("None of the service found")
        }
    }

    val weatherService: WeatherService by lazy {
        createRetrofitService(
            RetroFitServiceType.WEATHER_SERVICE
        ) as WeatherService
    }
}