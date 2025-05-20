package com.example.zenite.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PasswordStrengthIndicator(password: String) {
    val hasUppercase = password.any { it.isUpperCase() }
    val hasNumber = password.any { it.isDigit() }
    val hasSymbol = password.any { !it.isLetterOrDigit() }
    val isLongEnough = password.length >= 8

    val strength = listOf(hasUppercase, hasNumber, hasSymbol, isLongEnough).count { it }

    val color = when (strength) {
        4 -> Color.Green
        3 -> Color.Yellow
        2 -> Color(0xFFFFA500)
        else -> Color.Red
    }

    val text = when (strength) {
        4 -> "Senha forte"
        3 -> "Boa"
        2 -> "Fraca"
        else -> "Muito fraca"
    }

    Row(
        modifier = Modifier.padding(top = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .height(6.dp)
                .fillMaxWidth(fraction = strength / 4f)
                .background(color, shape = RoundedCornerShape(3.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, fontSize = 12.sp, color = color)
    }
}
