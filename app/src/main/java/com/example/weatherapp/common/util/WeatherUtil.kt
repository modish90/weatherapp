package com.example.weatherapp.common.util
import com.example.weatherapp.R

object WeatherUtil {

    val errorMessageMap by lazy {
        mapOf(
            "1002" to R.string.key_not_provided,
            "1003" to R.string.city_not_found,
            "1005" to R.string.url_invalid,
            "1006" to R.string.no_location,
            "2006" to R.string.invalid_key,
            "2007" to R.string.exceeded_per_quota,
            "2008" to R.string.disabled_api_key,
            "9999" to R.string.internal_server_error,
            WeatherUIConstants.ERROR_GENERIC to R.string.error_generic,
        )
    }
}