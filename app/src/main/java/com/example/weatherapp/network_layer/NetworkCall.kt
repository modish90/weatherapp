package com.example.weatherapp.network_layer

import retrofit2.Response

class NetworkCall {

    suspend fun <T : Any> retrofitCall(call: suspend () -> Response<T>): NetworkResponse<T> {
        var networkResponse: NetworkResponse<T>
        try {
            val response: Response<T> = call.invoke()
            networkResponse = if (response.isSuccessful)
                NetworkResponse.Success(data = response.body(), httpCode = response.code())
            else
                NetworkResponse.Failure(response.code(), response.errorBody())
            return networkResponse
        } catch (exception: Exception) {
            networkResponse = NetworkResponse.Error(exception = exception)
        }
        return networkResponse
    }
}