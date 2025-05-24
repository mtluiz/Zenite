package com.example.zenite.ui.screens.daily_quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zenite.data.local.entity.QuestionEntity
import com.example.zenite.ui.layout.ZeniteScreen
import com.example.zenite.ui.theme.LightBlue

@Composable
fun DailyQuizScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: DailyQuizViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val userAnswers by viewModel.userAnswers.collectAsState()
    
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { 
                showSuccessDialog = false
                navController.popBackStack()
            },
            title = { Text("Sucesso!") },
            text = { Text("Suas respostas foram enviadas com sucesso.") },
            confirmButton = {
                TextButton(onClick = {
                    showSuccessDialog = false
                    navController.popBackStack()
                }) {
                    Text("OK")
                }
            }
        )
    }
    
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Erro") },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
    
    ZeniteScreen(
        title = "Questionário Diário",
        navController = navController
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (uiState) {
                is DailyQuizUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                
                is DailyQuizUiState.Error -> {
                    Column(
                modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
            ) {
                Text(
                            text = (uiState as DailyQuizUiState.Error).message,
                            color = Color.Red,
                            fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
                }
                
                is DailyQuizUiState.Success -> {
                    val questionnaire = (uiState as DailyQuizUiState.Success).questionnaire
                    val questions = questionnaire.questions
                    
                    if (questions.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Este questionário não possui perguntas.",
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = questionnaire.title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            
                            if (questionnaire.description.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = questionnaire.description,
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 24.dp)
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(32.dp))
                            
                            Text(
                                text = "Pergunta ${currentQuestionIndex + 1} de ${questions.size}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            val currentQuestion = questions[currentQuestionIndex]
                            Text(
                                text = currentQuestion.text,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            when (currentQuestion.type) {
                                "multiple_choice" -> {
                                    MultipleChoiceQuestion(
                                        question = currentQuestion,
                                        selectedAnswer = userAnswers[currentQuestion.id],
                                        onAnswerSelected = { answer ->
                                            viewModel.updateAnswer(currentQuestion.id, answer)
                                        }
                                    )
                                }
                                "text" -> {
                                    TextQuestion(
                                        question = currentQuestion,
                                        answer = userAnswers[currentQuestion.id] ?: "",
                                        onAnswerChanged = { answer ->
                                            viewModel.updateAnswer(currentQuestion.id, answer)
                                        }
                                    )
                                }
                                else -> {
                                    Text(
                                        text = "Tipo de pergunta não suportado: ${currentQuestion.type}",
                                        color = Color.Red
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(32.dp))
                            
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                if (currentQuestionIndex > 0) {
                                    CustomBlueButton(
                                        text = "Anterior",
                                        onClick = {
                                            currentQuestionIndex--
                                        }
                                    )
                                } else {
                                    Spacer(modifier = Modifier.width(91.dp))
                                }
                                
                                if (currentQuestionIndex < questions.size - 1) {
                                    CustomBlueButton(
                                        text = "Próximo",
                                        onClick = {
                                            currentQuestionIndex++
                                        }
                                    )
                                } else {
                                    CustomBlueButton(
                                        text = "Finalizar",
                                        onClick = {
                                            if (viewModel.areAllQuestionsAnswered()) {
                                                viewModel.submitAnswers(
                                                    onSuccess = { showSuccessDialog = true },
                                                    onError = { message ->
                                                        errorMessage = message
                                                        showErrorDialog = true
                                                    }
                                                )
                                            } else {
                                                errorMessage = "Por favor, responda todas as perguntas."
                                                showErrorDialog = true
                                            }
                                        }
            )
        }
    }
                            
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextQuestion(
    question: QuestionEntity,
    answer: String,
    onAnswerChanged: (String) -> Unit
) {
    TextField(
        value = answer,
        onValueChange = onAnswerChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        label = { Text("Sua resposta") }
    )
}

@Composable
fun MultipleChoiceQuestion(
    question: QuestionEntity,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        question.options?.forEachIndexed { index, option ->
            OptionItem(
                option = option,
                isSelected = option == selectedAnswer,
                onClick = { onAnswerSelected(option) }
            )
            
            if (index < question.options.size - 1) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun OptionItem(
    option: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
                modifier = Modifier
            .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(LightBlue)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
        BulletCheckbox(isChecked = isSelected)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
            text = option,
                        fontSize = 16.sp,
                        color = Color.White
                    )
    }
}

@Composable
fun BulletCheckbox(isChecked: Boolean) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .border(2.dp, Color.Gray, CircleShape)
            .background(Color.White, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (isChecked) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(LightBlue, CircleShape)
            )
        }
    }
}

@Composable
fun CustomBlueButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(39.dp)
            .width(91.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(LightBlue)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}