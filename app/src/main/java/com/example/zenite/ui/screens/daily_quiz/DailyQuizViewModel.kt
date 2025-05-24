package com.example.zenite.ui.screens.daily_quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zenite.data.local.entity.QuestionnaireEntity
import com.example.zenite.data.preferences.UserPreferences
import com.example.zenite.data.repository.QuestionnaireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyQuizViewModel @Inject constructor(
    private val questionnaireRepository: QuestionnaireRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow<DailyQuizUiState>(DailyQuizUiState.Loading)
    val uiState: StateFlow<DailyQuizUiState> = _uiState

    private val _userAnswers = MutableStateFlow<Map<String, String>>(emptyMap())
    val userAnswers: StateFlow<Map<String, String>> = _userAnswers

    private var currentQuestionnaireId: String? = null

    init {
        loadQuestionnaires()
    }

    private fun loadQuestionnaires() {
        viewModelScope.launch {
            try {
                val result = questionnaireRepository.loadQuestionnaires()
                
                if (result.isSuccess && result.getOrNull()?.isNotEmpty() == true) {
                    val questionnaire = result.getOrNull()!!.first()
                    currentQuestionnaireId = questionnaire.id
                    _uiState.value = DailyQuizUiState.Success(questionnaire)
                } else {
                    _uiState.value = DailyQuizUiState.Error("Não foi possível carregar o questionário")
                }
            } catch (e: Exception) {
                _uiState.value = DailyQuizUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun updateAnswer(questionId: String, answer: String) {
        val currentAnswers = _userAnswers.value.toMutableMap()
        currentAnswers[questionId] = answer
        _userAnswers.value = currentAnswers
    }

    fun submitAnswers(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val userCode = userPreferences.getUserCode() ?: ""
                
                if (userCode.isBlank()) {
                    onError("Código de usuário não encontrado. Por favor, faça login novamente.")
                    return@launch
                }
                
                val questionnaireId = currentQuestionnaireId
                if (questionnaireId == null) {
                    onError("Questionário não encontrado")
                    return@launch
                }
                
                val answersList = _userAnswers.value.map { (questionId, answer) ->
                    Pair(questionId, answer)
                }
                
                if (answersList.isEmpty()) {
                    onError("Nenhuma resposta fornecida")
                    return@launch
                }
                
                val result = questionnaireRepository.submitAnswers(
                    userCode = userCode,
                    questionnaireId = questionnaireId,
                    answers = answersList
                )
                
                if (result.isSuccess) {
                    _userAnswers.value = emptyMap()
                    onSuccess()
                } else {
                    onError(result.exceptionOrNull()?.message ?: "Erro ao enviar respostas")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun areAllQuestionsAnswered(): Boolean {
        val currentState = _uiState.value
        if (currentState !is DailyQuizUiState.Success) return false
        
        val totalQuestions = currentState.questionnaire.questions.size
        val answeredQuestions = _userAnswers.value.size
        
        return answeredQuestions == totalQuestions
    }
}


sealed class DailyQuizUiState {
    object Loading : DailyQuizUiState()
    data class Success(val questionnaire: QuestionnaireEntity) : DailyQuizUiState()
    data class Error(val message: String) : DailyQuizUiState()
} 