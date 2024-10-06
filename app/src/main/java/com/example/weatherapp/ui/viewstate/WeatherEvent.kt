package com.example.weatherapp.ui.viewstate

import com.example.weatherapp.ui.data.utils.WeatherConstants.DEFAULT_WEATHER_DESTINATION
import com.example.weatherapp.ui.weather.SearchWidgetState

sealed class WeatherEvent {
    class GetCityWeatherDetails(val cityName: String = DEFAULT_WEATHER_DESTINATION) : WeatherEvent()
    class UpdateSearchTextState(val searchText: String) : WeatherEvent()
    class UpdateSearchWidgetState(val searchWidgetState: SearchWidgetState) : WeatherEvent()
}