package com.example.zenite.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private val _event = MutableStateFlow<SplashEvent>(SplashEvent.Idle)
    val event: StateFlow<SplashEvent> = _event

    init {
        viewModelScope.launch {
            delay(2000)
            _event.value = SplashEvent.NavigateToHome
        }
    }
}
