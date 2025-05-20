package com.example.zenite.ui.splash

sealed class SplashEvent {
    object Idle : SplashEvent()
    object NavigateToHome : SplashEvent()
    object NavigateToLogin : SplashEvent()
}
