package com.example.zenite.ui.screens.check_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckInViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(CheckInUiState())
    val uiState: StateFlow<CheckInUiState> = _uiState

    private val _selectedMood = MutableStateFlow<Int?>(null)
    val selectedMood: StateFlow<Int?> = _selectedMood

    private val _last3Days = MutableStateFlow(
        listOf(
            MoodDay("Qua", 3, true),
            MoodDay("Qui", 2, true),
            MoodDay("Sex", null, false)
        )
    )
    val last3Days: StateFlow<List<MoodDay>> = _last3Days

    init {
        updateCurrentTime()
        _uiState.update {
            it.copy(
                locationName = "Zenite Headquarters",
                address = "Av. Paulista, 1000 - Bela Vista, São Paulo - SP"
            )
        }

        viewModelScope.launch {
            while (true) {
                delay(1000)
                updateCurrentTime()
            }
        }
    }

    private fun updateCurrentTime() {
        _uiState.update {
            it.copy(currentTime = System.currentTimeMillis())
        }
    }

    fun selectMood(index: Int) {
        _selectedMood.value = index
    }

    fun submitCheckin(onComplete: () -> Unit) {
        val mood = _selectedMood.value ?: return
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            delay(1500)

            val updated = _last3Days.value.toMutableList()
            updated[2] = MoodDay("Sex", mood, true)
            _last3Days.value = updated
            _selectedMood.value = null

            _uiState.update {
                it.copy(
                    isLoading = false,
                    lastCheckIn = CheckInRecord(
                        location = _uiState.value.locationName,
                        timestamp = _uiState.value.currentTime
                    )
                )
            }

            onComplete()
        }
    }
}

data class MoodDay(
    val day: String,
    val mood: Int?,
    val checked: Boolean
)

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