package com.example.zenite.ui.screens.welcome

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zenite.data.preferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {
    private val _state = mutableStateOf<WelcomeState>(WelcomeState.Loading)
    val state: State<WelcomeState> = _state

    init {
        viewModelScope.launch {
            val alreadySeen = userPreferences.hasSeenWelcome()
            _state.value = if (alreadySeen) WelcomeState.NavigateHome else WelcomeState.ShowWelcome
        }
    }

    fun onWelcomeDone() {
        viewModelScope.launch {
            userPreferences.setWelcomeSeen(true)
            _state.value = WelcomeState.NavigateHome
        }
    }
}

sealed class WelcomeState {
    object Loading : WelcomeState()
    object ShowWelcome : WelcomeState()
    object NavigateHome : WelcomeState()
}
