package com.example.zenite.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.zenite.ui.layout.ZeniteScreen

@Composable
fun HomeScreen() {
    ZeniteScreen(title = "Home") { padding ->
        Column(Modifier.padding(padding)) {
            Text("Conteúdo da Home")
        }
    }
}
