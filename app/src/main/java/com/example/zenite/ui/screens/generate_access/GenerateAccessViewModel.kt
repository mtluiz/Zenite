package com.example.zenite.ui.screens.generate_access

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zenite.data.preferences.UserPreferences
import com.example.zenite.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenerateAccessViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _fullName = MutableStateFlow("")
    val fullName: StateFlow<String> = _fullName

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _repeatPassword = MutableStateFlow("")
    val repeatPassword: StateFlow<String> = _repeatPassword
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage
    
    private val _userCode = MutableStateFlow<String?>(null)
    val userCode: StateFlow<String?> = _userCode

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
    
    fun register(): Boolean {
        if (!isValid()) {
            _errorMessage.value = "Verifique os dados. Senha precisa ter pelo menos 8 caracteres."
            return false
        }
        
        _isLoading.value = true
        _errorMessage.value = null
        
        viewModelScope.launch {
            try {
                val result = userRepository.register(_password.value)
                
                result.fold(
                    onSuccess = { code ->
                        userPreferences.saveUserCode(code)
                        _userCode.value = code
                        _isLoading.value = false
                    },
                    onFailure = { error ->
                        _errorMessage.value = error.message ?: "Falha ao registrar. Tente novamente."
                        _isLoading.value = false
                    }
                )
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Erro desconhecido. Tente novamente."
                _isLoading.value = false
            }
        }
        
        return true
    }
}