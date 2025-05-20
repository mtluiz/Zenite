package com.example.zenite.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.zenite.ui.middleware.AuthGate
import com.example.zenite.ui.screens.auth.AuthViewModel
import com.example.zenite.ui.screens.avatars.AvatarViewModel

@Composable
fun ZeniteHomePage() {
    val navController = rememberNavController()

    val authViewModel: AuthViewModel = hiltViewModel()
    val avatarViewModel: AvatarViewModel = hiltViewModel()

    AuthGate(
        authViewModel = authViewModel,
        onAuthenticated = { navController.navigate("home") },
        onUnauthenticated = { navController.navigate("login") }
    )

    ZeniteNavGraph(
        navController = navController,
        authViewModel = authViewModel,
        avatarViewModel = avatarViewModel
    )
}
