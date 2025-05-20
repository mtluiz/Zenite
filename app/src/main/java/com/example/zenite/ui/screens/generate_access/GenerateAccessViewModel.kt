package com.example.zenite.ui.screens.generate_access

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GenerateAccessViewModel @Inject constructor() : ViewModel() {

    private val _fullName = MutableStateFlow("")
    val fullName: StateFlow<String> = _fullName

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _repeatPassword = MutableStateFlow("")
    val repeatPassword: StateFlow<String> = _repeatPassword

    fun onFullNameChange(value: String) {
        _fullName.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }

    fun onRepeatPasswordChange(value: String) {
        _repeatPassword.value = value
    }

    fun passwordsMatch(): Boolean = _password.value == _repeatPassword.value

    fun isValid(): Boolean {
        val pwd = _password.value
        val name = _fullName.value
        return name.length >= 3 && pwd.length >= 8 && passwordsMatch()
    }
}