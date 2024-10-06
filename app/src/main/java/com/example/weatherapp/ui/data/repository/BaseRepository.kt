package com.example.weatherapp.ui.data.repository

import com.example.weatherapp.ui.data.utils.WeatherUtils
import com.example.weatherapp.network_layer.NetworkCall
import com.example.weatherapp.network_layer.NetworkClientFactory
import com.example.weatherapp.network_layer.NetworkClientType
import com.example.weatherapp.network_layer.NetworkResponse
import com.example.weatherapp.ui.domain.models.response.ErrorWrapper
import com.example.weatherapp.ui.domain.models.response.InternetNotAvailableException
import com.example.weatherapp.ui.domain.models.response.ResponseWrapper
import com.example.weatherapp.ui.domain.models.response.WeatherError
import com.example.weatherapp.ui.domain.utils.WeatherDomainConstants
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.reflect.KClass
import javax.inject.Inject

open class BaseRepository {
    
    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>,
        errorClass: KClass<*> = WeatherError::class
    ): ResponseWrapper<T> {
        return if (WeatherUtils.isOnline()) {
            when (val networkResponse = NetworkCall().retrofitCall(call)) {
                is NetworkResponse.Success -> ResponseWrapper.Success(
                    data = networkResponse.data,
                    httpCode = networkResponse.httpCode
                )
                is NetworkResponse.Failure -> ResponseWrapper.Error(
                    parseApiError(
                        networkResponse.errorBody,
                        errorClass
                    ), httpCode = networkResponse.httpCode
                )
                is NetworkResponse.Error -> getError(networkResponse.exception)
            }
        } else {
            val exception = InternetNotAvailableException()
            getError(exception)
        }
    }

    private fun <T : Any> getError(exception: Exception): ResponseWrapper<T> {
        val stackTrace = StringBuilder()

        try {
            exception.stackTrace.forEach {
                stackTrace.append("\tat ${it ?: ""}")
            }
        } catch (e: Exception) {
            stackTrace.append("Could not get Stacktrace!")
        }
        return when (exception) {
            is InternetNotAvailableException -> ResponseWrapper.Error(
                ErrorWrapper(
                    shouldShowFromMap = true,
                    errorMessage = WeatherDomainConstants.INTERNET_ERROR,
                    exceptionStackTrace = stackTrace.toString()
                )
            )
            is UnknownHostException -> ResponseWrapper.Error(
                ErrorWrapper(
                    shouldShowFromMap = true,
                    errorMessage = WeatherDomainConstants.ERROR_UNKNOWN_HOST,
                    exceptionStackTrace = stackTrace.toString()
                )
            )
            is JsonDataException -> ResponseWrapper.Error(
                ErrorWrapper(
                    shouldShowFromMap = true,
                    errorMessage = exception.message.toString(),
                    exceptionStackTrace = stackTrace.toString()
                )
            )
            is SocketTimeoutException -> ResponseWrapper.Error(
                ErrorWrapper(
                    shouldShowFromMap = true,
                    errorMessage = WeatherDomainConstants.ERROR_TIMEOUT,
                    exceptionStackTrace = stackTrace.toString()
                )
            )
            is HttpException -> {
                ResponseWrapper.Error(
                    ErrorWrapper(
                        shouldShowFromMap = true,
                        errorMessage = exception.message.toString(),
                        exceptionStackTrace = stackTrace.toString()
                    )
                )
            }
            is IOException -> {
                ResponseWrapper.Error(
                    ErrorWrapper(
                        shouldShowFromMap = true,
                        errorMessage = WeatherDomainConstants.ERROR_IO,
                        exceptionStackTrace = stackTrace.toString()
                    )
                )
            }
            else -> {
                exception.cause?.let { causeLevel1 ->
                    if (causeLevel1 is RuntimeException) {
                        causeLevel1.cause?.let { causeLevel2 ->
                            if (isNetworkError(causeLevel2)) {
                                getNetworkError(stackTrace.toString())
                            } else {
                                getOtherError(exception, stackTrace.toString())
                            }
                        } ?: getOtherError(exception, stackTrace.toString())
                    } else {
                        if (isNetworkError(causeLevel1)) {
                            getNetworkError(stackTrace.toString())
                        } else
                            getOtherError(exception, stackTrace.toString())
                    }
                } ?: getOtherError(exception, stackTrace.toString())
            }
        }
    }

    private fun isNetworkError(exception: Throwable) : Boolean {
        return when (exception) {
            is UnknownHostException -> true
            is SocketTimeoutException -> true
            is IOException -> true
            is InternetNotAvailableException -> true
            else -> false
        }
    }

    private fun <T : Any> getNetworkError(stackTrace: String) : ResponseWrapper<T> {
        return ResponseWrapper.Error(
            ErrorWrapper(
                shouldShowFromMap = true,
                errorMessage = WeatherDomainConstants.INTERNET_ERROR,
                exceptionStackTrace = stackTrace
            )
        )
    }

    private fun <T : Any> getOtherError(
            exception: Exception,
            stackTrace: String
    ) : ResponseWrapper<T> {
        return ResponseWrapper.Error(
            ErrorWrapper(
                shouldShowFromMap = true,
                errorMessage = exception.message ?: "",
                exceptionStackTrace = stackTrace
            )
        )
    }

    private fun parseApiError(error: ResponseBody?, clazz: KClass<*>): ErrorWrapper {
        val networkClient = NetworkClientFactory.getNetworkClient(
            NetworkClientType.RETROFIT_CLIENT
        )
        return error?.string()?.let {

            if (it.isEmpty()) {
                return genericErrorMessage()
            }
            try {
                when (clazz) {
                    WeatherError::class -> {
                        val adapter: JsonAdapter<WeatherError> =
                            networkClient.getMoshiClient().adapter(WeatherError::class.java)
                        ErrorWrapper(
                            shouldShowFromMap = false,
                            errorCode = adapter.fromJson(it)?.error?.code.toString(),
                            errorMessage = adapter.fromJson(it)?.error?.message ?: ""
                        )
                    }
                    else -> genericErrorMessage()
                }
            } catch (e: Exception) {
                ErrorWrapper(
                    shouldShowFromMap = false,
                    errorMessage = e.message ?: ""
                )
            }
        } ?: genericErrorMessage()
    }

    protected fun genericErrorMessage(): ErrorWrapper {
        return ErrorWrapper(
            shouldShowFromMap = true,
            errorMessage = WeatherDomainConstants.ERROR_GENERIC
        )
    }
}