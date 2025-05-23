package com.example.zenite.ui.screens.check_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CheckInViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(CheckInUiState())
    val uiState: StateFlow<CheckInUiState> = _uiState
    
    init {
        // Inicializa com a hora atual
        updateCurrentTime()
        
        // Simulação de dados de localização (em um app real, viria do GPS)
        _uiState.update { it.copy(
            locationName = "Zenite Headquarters",
            address = "Av. Paulista, 1000 - Bela Vista, São Paulo - SP"
        )}
        
        // Atualiza o horário a cada segundo
        viewModelScope.launch {
            while(true) {
                delay(1000)
                updateCurrentTime()
            }
        }
    }
    
    private fun updateCurrentTime() {
        _uiState.update { it.copy(currentTime = System.currentTimeMillis()) }
    }
    
    fun performCheckIn() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            // Simulação de chamada de API para registrar o check-in
            delay(1500)
            
            // Registra o check-in no histórico
            val checkIn = CheckInRecord(
                location = _uiState.value.locationName,
                timestamp = _uiState.value.currentTime
            )
            
            // Em um app real, você salvaria este registro em um banco de dados ou enviaria para um servidor
            
            _uiState.update { it.copy(
                isLoading = false,
                lastCheckIn = checkIn
            )}
        }
    }
}

data class CheckInUiState(
    val isLoading: Boolean = false,
    val locationName: String = "",
    val address: String = "",
    val currentTime: Long = System.currentTimeMillis(),
    val lastCheckIn: CheckInRecord? = null
)

data class CheckInRecord(
    val location: String,
    val timestamp: Long
) 