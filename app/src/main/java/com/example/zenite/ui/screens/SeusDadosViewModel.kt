package com.example.zenite.ui.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SeusDadosViewModel : ViewModel() {
    private val _senha = MutableStateFlow("")
    val senha: StateFlow<String> = _senha

    private val _showAlteracoesSalvas = MutableStateFlow(false)
    val showAlteracoesSalvas: StateFlow<Boolean> = _showAlteracoesSalvas

    fun onSenhaChange(newSenha: String) {
        _senha.value = newSenha
    }

    fun salvarAlteracoes() {
        _showAlteracoesSalvas.value = true
    }

    fun dismissAlteracoesSalvas() {
        _showAlteracoesSalvas.value = false
    }
}


