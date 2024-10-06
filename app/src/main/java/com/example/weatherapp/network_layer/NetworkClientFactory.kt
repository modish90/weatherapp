package com.example.weatherapp.network_layer

object NetworkClientFactory {

    fun getNetworkClient(
            networkClientType: NetworkClientType,
            logLevel: WmLogLevel = WmLogLevel.BODY,
            readWriteTimeout: Long = NetworkConstants.READ_WRITE_TIMEOUT,
            connectionTimeout: Long = NetworkConstants.CONNECTION_TIMEOUT
    ): NetworkClientStrategy<Any> {
        return when (networkClientType) {
            NetworkClientType.RETROFIT_CLIENT -> {
                RetrofitClient(logLevel, readWriteTimeout, connectionTimeout)
            }

            NetworkClientType.APOLLO_CLIENT -> TODO()
        }
    }

    enum class WmLogLevel {
        /**
         * Refer HttpLogginInterceptor Levels
         */
        NONE, BASIC, HEADERS, BODY
    }
}