package com.example.zenite.ui.screens.check_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zenite.data.preferences.UserPreferences
import com.example.zenite.data.repository.MoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CheckInViewModel @Inject constructor(
    private val moodRepository: MoodRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CheckInUiState())
    val uiState: StateFlow<CheckInUiState> = _uiState
    
    init {
        updateCurrentTime()
        
        _uiState.update { it.copy(
            locationName = "Zenite Headquarters",
            address = "Av. Paulista, 1000 - Bela Vista, São Paulo - SP"
        )}
        
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
    
    fun recordMood(selectedMood: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            try {
                val userCode = userPreferences.getUserCode()
                
                if (userCode != null) {
                    val result = moodRepository.addMood(
                        userCode = userCode,
                        mood = selectedMood
                    )
                    
                    result.fold(
                        onSuccess = {
                            _uiState.update { it.copy(
                                isLoading = false,
                                lastCheckIn = CheckInRecord(
                location = _uiState.value.locationName,
                                    timestamp = _uiState.value.currentTime,
                                    mood = selectedMood
                                ),
                                error = null
                            )}
                        },
                        onFailure = { error ->
                            _uiState.update { it.copy(
                                isLoading = false,
                                error = error.message ?: "Erro ao registrar humor"
                            )}
                        }
                    )
                } else {
                    _uiState.update { it.copy(
                        isLoading = false,
                        error = "Código de usuário não encontrado"
                    )}
                }
            } catch (e: Exception) {
            _uiState.update { it.copy(
                isLoading = false,
                    error = e.message ?: "Erro desconhecido"
            )}
            }
        }
    }
}

data class CheckInUiState(
    val isLoading: Boolean = false,
    val locationName: String = "",
    val address: String = "",
    val currentTime: Long = System.currentTimeMillis(),
    val lastCheckIn: CheckInRecord? = null,
    val error: String? = null
)

data class CheckInRecord(
    val location: String,
    val timestamp: Long,
    val mood: String? = null
) 