package com.example.zenite.ui.screens.mood

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zenite.R
import com.example.zenite.data.local.entity.MoodEntity
import com.example.zenite.ui.layout.ZeniteScreen
import com.example.zenite.ui.theme.GrayBlue
import com.example.zenite.ui.theme.LightBlue
import com.example.zenite.ui.theme.Primary
import com.example.zenite.ui.theme.White
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RememberReturnType")
@Composable
fun MoodScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: MoodViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val selectedMood = remember { mutableIntStateOf(-1) }
    var description by remember { mutableStateOf("") }
    val moodIcons = listOf("😁", "🙂", "😐", "🙁", "😠", "🤩", "😴")
    val moodLabels = listOf("Feliz", "Calmo", "Neutro", "Triste", "Ansioso", "Animado", "Cansado")
    
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    var selectedDate by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance()
    
    val showDatePicker = {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                selectedDate = dateFormatter.format(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
    
    LaunchedEffect(uiState.saveSuccess) {
        if (uiState.saveSuccess) {
            showSuccessDialog = true
            viewModel.clearSaveSuccess()
            
            selectedMood.value = -1
            description = ""
            selectedDate = ""
        }
    }
    
    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            showErrorDialog = true
        }
    }
    
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Sucesso") },
            text = { Text("Seu registro de humor foi salvo com sucesso!") },
            confirmButton = {
                Button(
                    onClick = { showSuccessDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF19BFB7))
                ) {
                    Text("OK")
                }
            }
        )
    }
    
    if (showErrorDialog && uiState.error != null) {
        AlertDialog(
            onDismissRequest = { 
                showErrorDialog = false
                viewModel.clearError()
            },
            title = { Text("Erro") },
            text = { Text(uiState.error ?: "Ocorreu um erro desconhecido") },
            confirmButton = {
                Button(
                    onClick = { 
                        showErrorDialog = false 
                        viewModel.clearError()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF19BFB7))
                ) {
                    Text("OK")
                }
            }
        )
    }

    ZeniteScreen(title = "Humor", navController = navController) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
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
                        .height(400.dp)
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
                        modifier = Modifier.padding(bottom = 8.dp, start = 12.dp)
                    )

                    Box(
                        modifier = Modifier
                            .width(300.dp)
                            .height(40.dp)
                            .background(White, shape = RoundedCornerShape(8.dp))
                            .clickable { showDatePicker() }
                            .padding(horizontal = 12.dp)
                            .align(Alignment.CenterHorizontally),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = if (selectedDate.isEmpty()) "Selecione uma data" else selectedDate,
                            color = if (selectedDate.isEmpty()) Color.Gray else Color.Black,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Como foi esse dia?",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = White,
                        modifier = Modifier.padding(bottom = 8.dp, start = 12.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        moodIcons.forEachIndexed { index, icon ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(horizontal = 1.dp)
                            ) {
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
                                Text(
                                    text = moodLabels[index],
                                    color = if (selectedMood.value == index) Color.Cyan else White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Text(
                        text = "O que contribuiu para esse sentimento?",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = White,
                        modifier = Modifier.padding(bottom = 8.dp, start = 12.dp)
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        modifier = Modifier
                            .width(300.dp)
                            .height(80.dp)
                            .align(Alignment.CenterHorizontally)
                            .background(White, shape = RoundedCornerShape(8.dp)),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = GrayBlue
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { 
                            if (selectedMood.value >= 0) {
                                val moodEmoji = moodIcons[selectedMood.value]
                                val apiMoods = listOf("happy", "calm", "neutral", "sad", "anxious", "excited", "tired")
                                val apiMood = apiMoods[selectedMood.value]
                                viewModel.saveMood(apiMood, description, selectedDate)
                            }
                        },
                        modifier = Modifier
                            .width(181.dp)
                            .height(29.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF19BFB7)),
                        enabled = selectedMood.value >= 0 && !uiState.isLoading
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = White,
                                strokeWidth = 2.dp
                            )
                        } else {
                        Text(text = "Salvar", color = White, fontSize = 12.sp)
                        }
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
                    if (uiState.isLoading && uiState.moods.isEmpty()) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth().height(100.dp)
                        ) {
                            CircularProgressIndicator(
                                color = White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    } else if (uiState.moods.isEmpty()) {
                        Text(
                            text = "Nenhum registro de humor encontrado",
                            color = White,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    } else {
                        uiState.moods.take(3).forEach { mood ->
                            MoodEntryItem(mood)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MoodEntryItem(mood: MoodEntity) {
    val dateFormatter = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale("pt", "BR"))
    val dateObject = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(mood.date)
    val formattedDate = dateObject?.let { dateFormatter.format(it) } ?: mood.date
    
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            Text(
            text = mood.mood.toEmoji(),
                                fontSize = 15.sp,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Column {
                                Text(
                text = formattedDate,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                text = mood.description ?: "",
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MoodScreenPreview() {
    MoodScreen()
}