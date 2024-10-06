package com.example.weatherapp.common

import android.app.Application
import com.example.weatherapp.ui.data.utils.WeatherUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        WeatherUtils.appContext = this
    }
}