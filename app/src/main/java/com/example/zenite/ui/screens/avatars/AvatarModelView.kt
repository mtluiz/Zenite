package com.example.zenite.ui.screens.avatars

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.zenite.util.getAllAvatars
import com.example.zenite.util.getRandomAvatar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AvatarViewModel(application: Application) : AndroidViewModel(application) {

    private val _avatarUrl = MutableStateFlow<String?>(null)
    val avatarUrl: StateFlow<String?> = _avatarUrl

    private val _avatarList = MutableStateFlow<List<String>>(emptyList())
    val avatarList: StateFlow<List<String>> = _avatarList

    init {
        loadAllAvatars()
        loadRandomAvatar()
    }

    fun loadAllAvatars() {
        viewModelScope.launch {
            _avatarList.value = getAllAvatars(getApplication())
        }
    }

    fun loadRandomAvatar() {
        viewModelScope.launch {
            _avatarUrl.value = getRandomAvatar(getApplication())
        }
    }

    fun selectAvatar(avatar: String) {
        _avatarUrl.value = avatar
    }
}
