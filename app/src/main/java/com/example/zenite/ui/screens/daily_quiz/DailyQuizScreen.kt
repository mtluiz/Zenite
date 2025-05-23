package com.example.zenite.ui.screens.daily_quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zenite.ui.layout.ZeniteScreen
import com.example.zenite.ui.theme.LightBlue

@Composable
fun DailyQuizScreen() {
    ZeniteScreen(title = "Relatório Diário") { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Avaliação de Risco Psicossocial no Local de Trabalho",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.widthIn(max = 329.dp),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            Text(text = "Pergunta x de x")

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Como você avalia sua carga de trabalho?")

            val tasks = listOf(
                "Fazer login",
                "Ver dashboard",
                "Sincronizar dados",
                "Finalizar sessão",
                "testeMaisUma"
            )

            BulletCheckList(items = tasks)

            NavigationButtonsRow(
                onPreviousClick = { println("Anterior clicado") },
                onNextClick = { println("Próximo clicado") }
            )
        }
    }
}

@Composable
fun BulletCheckList(items: List<String>) {
    val selectedIndex = remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items.forEachIndexed { index, item ->
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(LightBlue)
                    .clickable {
                        selectedIndex.value = if (selectedIndex.value == index) null else index
                    }
                    .padding(vertical = 5.dp, horizontal = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BulletCheckbox(isChecked = selectedIndex.value == index)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = item,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun BulletCheckbox(isChecked: Boolean) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .border(2.dp, Color.Gray, CircleShape)
            .background(Color.White, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (isChecked) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(LightBlue, CircleShape)
            )
        }
    }
}

@Composable
fun CustomBlueButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(39.dp)
            .width(91.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(LightBlue)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

@Composable
fun NavigationButtonsRow(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 39.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomBlueButton(
            text = "Anterior",
            onClick = onPreviousClick
        )
        CustomBlueButton(
            text = "Próximo",
            onClick = onNextClick
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DailyQuizScreenPreview() {
    DailyQuizScreen()
}