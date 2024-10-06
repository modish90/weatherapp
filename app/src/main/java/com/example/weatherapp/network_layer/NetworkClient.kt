package com.example.weatherapp.network_layer

import com.squareup.moshi.Moshi

interface NetworkClientStrategy<out T : Any> {
    fun getMoshiClient(): Moshi
    fun getNetworkClient(url: String = "", headers: Map<String, String> = mapOf()): T
}