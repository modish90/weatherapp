package com.example.weatherapp.di

import com.example.weatherapp.network_layer.NetworkCall
import com.example.weatherapp.ui.data.provider.RetrofitServiceProvider
import com.example.weatherapp.ui.data.repository.WeatherForecastRepositoryImpl
import com.example.weatherapp.ui.data.services.BaseService
import com.example.weatherapp.ui.domain.repository.WeatherForecastRepository
import com.example.weatherapp.ui.domain.usecases.GetWeatherForecastUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(weatherApi: BaseService): WeatherForecastRepository =
         WeatherForecastRepositoryImpl(weatherApi)

    @Provides
    fun getWeatherForecastUseCase() : GetWeatherForecastUseCase =
         GetWeatherForecastUseCase(provideRepository(RetrofitServiceProvider.weatherService))

    @Provides
    fun backgroundDispatcher() : CoroutineDispatcher  = Dispatchers.IO
}