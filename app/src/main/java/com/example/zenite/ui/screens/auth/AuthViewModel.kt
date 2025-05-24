package com.example.zenite.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zenite.data.preferences.UserPreferences
import com.example.zenite.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Por enquanto sera mocked. Futuramente Firebase

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val userRepository: UserRepository
) : ViewModel() {
    val isAuthenticated = MutableStateFlow(false)

    private val _userCode = MutableStateFlow<String?>(null)
    val userCode: StateFlow<String?> = _userCode
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadUserCode()
    }
    
    private fun loadUserCode() {
        viewModelScope.launch {
            try {
                _userCode.value = userPreferences.getUserCode()
            } catch (e: Exception) {
                // Ignora erros ao obter o código
            }
        }
    }

    fun login(code: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                val result = userRepository.login(code, password)
                
                result.fold(
                    onSuccess = {
        isAuthenticated.value = true
                        _isLoading.value = false
                    },
                    onFailure = { exception ->
                        _error.value = exception.message ?: "Falha ao autenticar"
                        _isLoading.value = false
                    }
                )
            } catch (e: Exception) {
                _error.value = e.message ?: "Erro desconhecido"
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        isAuthenticated.value = false
    }
}
