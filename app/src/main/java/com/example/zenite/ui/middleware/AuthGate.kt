package com.example.zenite.ui.middleware

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zenite.ui.screens.auth.AuthViewModel

@Composable
fun AuthGate(
    authViewModel: AuthViewModel = hiltViewModel(),
    onAuthenticated: () -> Unit,
    onUnauthenticated: () -> Unit
) {
    val isAuth by authViewModel.isAuthenticated.collectAsState()

    LaunchedEffect(isAuth) {
        if (isAuth) onAuthenticated()
        else onUnauthenticated()
    }
}
