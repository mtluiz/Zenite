package com.example.zenite.ui.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MudarAvatarViewModel : ViewModel() {
    private val _selectedAvatar = MutableStateFlow<Int?>(null)
    val selectedAvatar: StateFlow<Int?> = _selectedAvatar

    fun selectAvatar(avatarResId: Int) {
        _selectedAvatar.value = avatarResId
    }
}


