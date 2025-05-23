package com.example.zenite.ui.screens.mood

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zenite.R
import com.example.zenite.ui.layout.ZeniteScreen
import com.example.zenite.ui.theme.GrayBlue
import com.example.zenite.ui.theme.LightBlue
import com.example.zenite.ui.theme.Primary
import com.example.zenite.ui.theme.White
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RememberReturnType")
@Composable
fun MoodScreen() {

    val selectedMood = remember { mutableIntStateOf(-1) }
    var description by remember { mutableStateOf("") }
    val moodIcons = listOf("😄", "🙂", "😐", "☹️", "😡")

    val context = LocalContext.current
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    var selectedDate by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                selectedDate = dateFormatter.format(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    data class MoodEntry(val icon: String, val date: String, val description: String)
    val mockEntries = listOf(
        MoodEntry("🙂", "1 de maio de 2025", "Dia tranquilo em casa"),
        MoodEntry("😄", "2 de maio de 2025", "Saí com os amigos"),
        MoodEntry("😐", "3 de maio de 2025", "Dia normal no trabalho"),
        MoodEntry("🙂", "4 de maio de 2025", "Pude realizar todas minhas tarefas")
    )

    ZeniteScreen(title = "Humor") { padding ->
        Column(Modifier.padding(padding)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Registre",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 40.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    modifier = Modifier
                        .width(343.dp)
                        .height(330.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(LightBlue)
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Selecione a Data e seu Humor",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = White,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Data:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = White,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = selectedDate,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .width(300.dp)
                            .height(26.dp)
                            .align(Alignment.CenterHorizontally)
                            .clickable { datePickerDialog.show() }
                            .background(White, shape = RoundedCornerShape(8.dp)),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color(0xFF366588),
                            unfocusedBorderColor = GrayBlue,
                            focusedBorderColor = Primary
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Como foi esse dia?",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )

                    Row(modifier = Modifier.fillMaxWidth()) {
                        moodIcons.forEachIndexed { index, icon ->
                            Text(
                                text = icon,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(
                                        if (selectedMood.value == index) Color.Cyan else Color.Transparent
                                    )
                                    .clickable { selectedMood.value = index }
                                    .padding(12.dp)
                            )
                        }
                    }

                    Text(
                        text = "O que contribuiu para esse sentimento?",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = White,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        modifier = Modifier
                            .width(300.dp)
                            .height(80.dp)
                            .align(Alignment.CenterHorizontally)
                            .background(White, shape = RoundedCornerShape(8.dp)),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color(0xFF366588),
                            unfocusedBorderColor = GrayBlue,
                            focusedBorderColor = Primary
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { /* TODO: Salvar registros */ },
                        modifier = Modifier
                            .width(181.dp)
                            .height(29.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF19BFB7))
                    ) {
                        Text(text = "Salvar", color = White, fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Acompanhe seu humor",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 40.dp)
                )

                Spacer(modifier = Modifier.height(5.dp))

                Column(
                    modifier = Modifier
                        .width(343.dp)
                        .height(190.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(LightBlue)
                        .padding(29.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    mockEntries.takeLast(3).reversed().forEach { entry ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            Text(
                                text = entry.icon,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Column {
                                Text(
                                    text = entry.date,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = entry.description,
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MoodScreenPreview() {
    MoodScreen()
}