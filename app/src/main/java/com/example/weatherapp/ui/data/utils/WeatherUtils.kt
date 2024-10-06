package com.example.weatherapp.ui.data.utils

import android.content.Context
import android.net.ConnectivityManager

object WeatherUtils {

    var appContext: Context? = null

    fun isOnline(): Boolean {
        return appContext?.let {
            val connectivityManager =
                it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected
        } != false

    }
}