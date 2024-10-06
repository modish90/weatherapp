package com.example.weatherapp.network_layer

import com.example.weatherapp.ui.data.utils.WeatherConstants
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitClient(logLevel: NetworkClientFactory.WmLogLevel = NetworkClientFactory.WmLogLevel.NONE,
                     readWriteTimeout: Long = NetworkConstants.READ_WRITE_TIMEOUT,
                     connectionTimeout: Long = NetworkConstants.CONNECTION_TIMEOUT) :
        BaseClient(logLevel, readWriteTimeout, connectionTimeout),
        NetworkClientStrategy<Retrofit> {

    private val mRetrofit: Retrofit by lazy {
        val okHttpClient = mHttpClient.build()
        // IdlingResources.registerOkHttp(okHttpClient);
        Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(WeatherConstants.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
    }

    override fun getMoshiClient(): Moshi {
        return moshi
    }

    override fun getNetworkClient(url: String, headers: Map<String, String>): Retrofit {
        return mRetrofit
    }
}