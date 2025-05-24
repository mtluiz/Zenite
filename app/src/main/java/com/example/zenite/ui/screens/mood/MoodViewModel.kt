package com.example.zenite.ui.screens.mood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zenite.data.local.entity.MoodEntity
import com.example.zenite.data.preferences.UserPreferences
import com.example.zenite.data.repository.MoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MoodViewModel @Inject constructor(
    private val moodRepository: MoodRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoodUiState())
    val uiState: StateFlow<MoodUiState> = _uiState
    
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val displayDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    init {
        loadUserMoods()
    }
    
    private fun loadUserMoods() {
        viewModelScope.launch {
            val userCode = userPreferences.getUserCode()
            
            if (userCode != null) {
                moodRepository.syncMoods(userCode)
                
                moodRepository.observeMoodsByUserCode(userCode).collectLatest { moods ->
                    _uiState.value = _uiState.value.copy(
                        moods = moods,
                        isLoading = false
                    )
                }
            } else {
                _uiState.value = _uiState.value.copy(
                    error = "Código de usuário não encontrado. Por favor, faça login.",
                    isLoading = false
                )
            }
        }
    }
    
    fun saveMood(mood: String, description: String, date: String?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val userCode = userPreferences.getUserCode()
                
                if (userCode == null) {
                    _uiState.value = _uiState.value.copy(
                        error = "Código de usuário não encontrado. Por favor, faça login.",
                        isLoading = false
                    )
                    return@launch
                }
                
                val formattedDate = if (date.isNullOrEmpty()) {
                    dateFormat.format(Date())
                } else {
                    val parsedDate = displayDateFormat.parse(date)
                    dateFormat.format(parsedDate ?: Date())
                }
                
                val result = moodRepository.addMood(
                    userCode = userCode,
                    mood = mood,
                    description = description,
                    date = formattedDate
                )
                
                result.fold(
                    onSuccess = {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            saveSuccess = true
                        )
                    },
                    onFailure = { exception ->
                        _uiState.value = _uiState.value.copy(
                            error = exception.message ?: "Erro ao salvar o registro de humor",
                            isLoading = false
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Erro desconhecido",
                    isLoading = false
                )
            }
        }
    }
    
    fun clearSaveSuccess() {
        _uiState.value = _uiState.value.copy(saveSuccess = false)
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class MoodUiState(
    val moods: List<MoodEntity> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val saveSuccess: Boolean = false
)

fun String.toEmoji(): String {
    return when (this.lowercase(Locale.getDefault())) {
        "happy" -> "😁"
        "calm" -> "🙂"
        "neutral" -> "😐"
        "sad" -> "🙁"
        "anxious" -> "😠"
        "excited" -> "🤩"
        "tired" -> "😴"
        else -> "😐" // Default para neutral
    }
}

fun String.toMoodString(): String {
    return when (this) {
        "😁" -> "Feliz"
        "🙂" -> "Calmo"
        "😐" -> "Neutro"
        "🙁" -> "Triste"
        "😠" -> "Ansioso"
        "🤩" -> "Animado"
        "😴" -> "Cansado"
        "happy" -> "Feliz"
        "calm" -> "Calmo"
        "neutral" -> "Neutro"
        "sad" -> "Triste"
        "anxious" -> "Ansioso"
        "excited" -> "Animado"
        "tired" -> "Cansado"
        else -> "Neutro" // Default
    }
}

fun String.toApiMoodString(): String {
    return when (this) {
        "😁", "Muito bem" -> "happy"
        "🙂", "Bem" -> "calm"
        "😐", "Neutro" -> "neutral"
        "🙁", "Mal" -> "sad"
        "😠", "Muito mal" -> "anxious"
        else -> "neutral" // Default
    }
} 