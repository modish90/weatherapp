package com.example.weatherapp.common

sealed class ScreenState<out T> {
    object Loading : ScreenState<Nothing>()
    class Render<T>(val renderState: T) : ScreenState<T>()
    class Initialize : ScreenState<Nothing>()
}