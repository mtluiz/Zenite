package com.example.zenite.ui.screens.questionnaire

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zenite.ui.screens.daily_quiz.DailyQuizScreen

@Composable
fun QuestionnaireScreen(navController: NavHostController = rememberNavController()) {
    DailyQuizScreen(navController = navController)
} 