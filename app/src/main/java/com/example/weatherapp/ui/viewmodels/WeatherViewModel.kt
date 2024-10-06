package com.example.weatherapp.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.InMemoryCache
import com.example.weatherapp.common.base.BaseViewModel
import com.example.weatherapp.ui.data.utils.WeatherConstants.DEFAULT_WEATHER_DESTINATION
import com.example.weatherapp.ui.domain.models.response.ResponseWrapper
import com.example.weatherapp.ui.domain.models.response.Weather
import com.example.weatherapp.ui.domain.usecases.GetWeatherForecastUseCase
import com.example.weatherapp.ui.viewstate.WeatherEvent
import com.example.weatherapp.ui.viewstate.WeatherState
import com.example.weatherapp.ui.weather.SearchWidgetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val getWeatherForecastUseCase: GetWeatherForecastUseCase,
                       private val dispatcher: CoroutineDispatcher
) : BaseViewModel<WeatherEvent, WeatherState>() {

    private val _searchTextState: MutableState<String> = mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState
    val inCacheManager = InMemoryCache<Weather>()

    override fun handleEvent(publishedEvent: WeatherEvent) {
        when (publishedEvent) {
            is WeatherEvent.GetCityWeatherDetails -> getWeatherDetails(
                publishedEvent.cityName.lowercase(Locale.ROOT)
            )
            is WeatherEvent.UpdateSearchTextState -> updateSearchTextState(publishedEvent.searchText)
            is WeatherEvent.UpdateSearchWidgetState -> updateSearchWidgetState(publishedEvent.searchWidgetState)
        }
    }

    init {
        getWeatherDetails(DEFAULT_WEATHER_DESTINATION)
    }

    private fun getWeatherDetails(cityName: String) {
        setState(WeatherState.LOADING)

        viewModelScope.launch(dispatcher) {
            val cachedWeather = inCacheManager.get(cityName)
            cachedWeather?.let {
                setState(WeatherState.Success(it))
            } ?: run {
                when (val cityWeatherDetails =
                    getWeatherForecastUseCase.invoke(
                        cityName
                    )) {
                    is ResponseWrapper.Error -> {
                        setState(WeatherState.Error(
                            getErrorWrapper(
                                errorWrapper = cityWeatherDetails.errorWrapper,
                                customError = cityWeatherDetails.errorWrapper.errorCode
                            )
                        ))
                    }
                    is ResponseWrapper.Success -> {
                        cityWeatherDetails.data?.let {
                            inCacheManager.put(cityName, it)
                            setState(WeatherState.Success(it))
                        } ?: run {

                        }
                    }
                }
            }
        }
    }

    private fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    private fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }
}