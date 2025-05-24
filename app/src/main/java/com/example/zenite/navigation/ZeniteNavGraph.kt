package com.example.zenite.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.zenite.ui.screens.auth.AuthViewModel
import com.example.zenite.ui.screens.auth.LoginScreen
import com.example.zenite.ui.screens.avatars.AvatarViewModel
import com.example.zenite.ui.screens.avatars.ChooseAvatarScreen
import com.example.zenite.ui.screens.demo.ZeniteHomeScreen
import com.example.zenite.ui.screens.generate_access.GenerateAccessScreen
import com.example.zenite.ui.screens.generate_access.GenerateAccessViewModel
import com.example.zenite.ui.screens.check_in.CheckInScreen
import com.example.zenite.ui.screens.mood.MoodScreen
import com.example.zenite.ui.screens.personal_evolution.PersonalEvolutionScreen
import com.example.zenite.ui.screens.questionnaire.QuestionnaireScreen
import com.example.zenite.ui.screens.support.SupportScreen

@Composable
fun ZeniteNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    avatarViewModel: AvatarViewModel = hiltViewModel()
) {
    NavHost(navController = navController, startDestination = "generate_access") {

        composable("generate_access") {
            val generateAccessViewModel: GenerateAccessViewModel = hiltViewModel()
            val userCode = generateAccessViewModel.userCode.collectAsState().value
            
            GenerateAccessScreen(
                onAccessAccount = { navController.navigate("login") },
                onGenerateCode = { 
                    navController.navigate("home") {
                        popUpTo("generate_access") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onOpenAvatarScreen = { navController.navigate("choose_avatar") },
                onBack = { navController.popBackStack() }
            )
        }

        composable("login") {
            LoginScreen(
                onLoginClick = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onForgotPassword = {},
                onGenerateAccess = { navController.navigate("generate_access") }
            )
        }

        composable("choose_avatar") {
            val avatars = avatarViewModel.avatarList.collectAsState().value
            ChooseAvatarScreen(
                avatars = avatars,
                onAvatarSelected = {
                    avatarViewModel.selectAvatar(it)
                    navController.popBackStack()
                },
                onSaveClick = { navController.popBackStack() }
            )
        }

        composable("home") {
            ZeniteHomeScreen(navController = navController)
        }

        composable("check_in") {
            CheckInScreen(
                navController = navController,
                onCheckInComplete = { navController.navigate("home") }
            )
        }

        composable("mood") {
            MoodScreen(
                navController = navController
            )
        }

        composable("evolution") {
            PersonalEvolutionScreen(navController = navController)
        }
        
        composable("support") {
            SupportScreen(navController = navController)
        }
        
        composable("diary") {
            QuestionnaireScreen(navController = navController)
        }
    }
}
