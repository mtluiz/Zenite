package com.example.zenite.ui.screens.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

// Por enquanto sera mocked. Futuramente Firebase

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    val isAuthenticated = MutableStateFlow(false)

    fun login() {
        isAuthenticated.value = true
    }

    fun logout() {
        isAuthenticated.value = false
    }
}
