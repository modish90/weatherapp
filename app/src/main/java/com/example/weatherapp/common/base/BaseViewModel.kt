package com.example.weatherapp.common.base

import androidx.lifecycle.ViewModel
import com.example.weatherapp.common.ScreenState
import com.example.weatherapp.ui.domain.models.response.ErrorWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<in Event, State> : ViewModel() {

    protected val _state: MutableStateFlow<ScreenState<State>> = MutableStateFlow(value = ScreenState.Initialize())

    val state: StateFlow<ScreenState<State>> = _state.asStateFlow()

    fun dispatchEvent(event: Event) {
        handleEvent(event)
    }

    protected fun setState(state: State) {
        _state.value = ScreenState.Render(state)
    }

    protected abstract fun handleEvent(publishedEvent: Event)

    fun getErrorWrapper(
        errorWrapper: ErrorWrapper,
        customError: String = errorWrapper.errorMessage
    ): ErrorWrapper {
        return when {
            errorWrapper.shouldShowFromMap.not() -> {
                ErrorWrapper(
                    shouldShowFromMap = true,
                    errorMessage = customError
                )
            }
            else -> {
                errorWrapper
            }
        }
    }
}