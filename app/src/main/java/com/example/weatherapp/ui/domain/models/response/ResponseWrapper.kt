package com.example.weatherapp.ui.domain.models.response

import com.example.weatherapp.ui.domain.utils.WeatherDomainConstants

sealed class ResponseWrapper<out T : Any> {
    data class Success<T : Any>(
        val data: T? = null,
        val httpCode: Int? = null
    ) : ResponseWrapper<T>()

    data class Error<out T : Any>(val errorWrapper: ErrorWrapper, val httpCode: Int? = null) : ResponseWrapper<T>()
}

data class ErrorWrapper(
    val shouldShowFromMap: Boolean = false,
    val errorMessage: String = WeatherDomainConstants.ERROR_GENERIC,
    val exceptionStackTrace: String = "",
    val errorCode: String = ""
)

class InternetNotAvailableException : Exception()

sealed class ServerException(error: Throwable) : RuntimeException(error) {

    class NoNetworkException(error: Throwable) : ServerException(error)

    class ServerUnreachableException(error: Throwable) : ServerException(error)

    class JsonDataException(error: Throwable) : ServerException(error)

    class HttpCallFailureException(error: Throwable) :
        ServerException(error)

}