package com.example.weatherapp.network_layer.intercepter

import okhttp3.Interceptor
import okhttp3.Response


class RetrofitInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        builder.addHeader("Cookie", "")
            .addHeader("Version", "")

        return chain.proceed(builder.build())
    }
}