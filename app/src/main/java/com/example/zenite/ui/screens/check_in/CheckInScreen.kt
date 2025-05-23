package com.example.zenite.ui.screens.check_in

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.zenite.ui.layout.ZeniteScreen
import com.example.zenite.ui.theme.White
import com.example.zenite.ui.theme.Blueberry
import com.example.zenite.ui.theme.LightBlueberry
import com.example.zenite.ui.theme.ZeniteFonts
import kotlinx.coroutines.delay

@Composable
fun CheckInScreen(
    viewModel: CheckInViewModel = hiltViewModel(),
    navController: NavController,
    onCheckInComplete: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    
    var checkInCompleted by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var remainingTimeInSeconds by remember { mutableStateOf(24 * 60 * 60L) }
    
    LaunchedEffect(checkInCompleted) {
        if (checkInCompleted) {
            while (remainingTimeInSeconds > 0) {
                delay(1000)
                remainingTimeInSeconds -= 1
            }
            checkInCompleted = false
        }
    }
    
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Sucesso") },
            text = { Text("Check-in realizado com sucesso!") },
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
    
    ZeniteScreen(title = "Check-in", navController = navController as NavHostController) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            CurrentMoodCard(
                locationName = uiState.locationName,
                address = uiState.address
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                modifier = Modifier.padding(horizontal = 25.dp),
                text = if (!checkInCompleted) "Check-in disponível" else formatRemainingTime(remainingTimeInSeconds),
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                fontFamily = ZeniteFonts.Quicksand
            )
            
            TimeInfoCard(
                onCheckInClick = {
                    checkInCompleted = true
                    showSuccessDialog = true
                },
                isCheckInEnabled = !checkInCompleted
            )
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

fun formatRemainingTime(seconds: Long): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60
    return String.format("Próximo check-in em: %02d:%02d:%02d", hours, minutes, secs)
}

@Composable
fun CurrentMoodCard(locationName: String, address: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Blueberry
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {    
            MoodSelector()
        }
    }
}

@Composable
fun MoodSelector() {
    Card(
        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 22.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DayMoodCard(
                day = "Qua",
            )
            
            DayMoodCard(
                day = "Qui",
            )
            
            DayMoodCard(
                day = "Sex",
            )
        }
    }
}

@Composable
fun DayMoodCard(
    day: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {
        Card(
            modifier = Modifier
                .width(87.dp)
                .height(114.dp)
                .padding(horizontal = 2.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = LightBlueberry
            ),
        ) {
            Box(
                modifier = Modifier
                    .width(87.dp)
                    .height(114.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = "file:///android_asset/check_in/smile-face.png",
                    contentDescription = "Mood avatar",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(10.dp))
        
        Text(
            text = day,
            style = MaterialTheme.typography.bodyMedium,
            color = White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TimeInfoCard(
    onCheckInClick: () -> Unit = {},
    isCheckInEnabled: Boolean = true
) { 
    var selectedMood by remember { mutableStateOf<String?>(null) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(
            containerColor = Blueberry
        )
    ) {
        Column(
            modifier = Modifier.padding(25.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier,
                    text = "Como você está se sentindo hoje?",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    fontFamily = ZeniteFonts.Quicksand
                )
            }
            
            Spacer(modifier = Modifier.height(22.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            ) {
                CheckInButton(
                    emoji = "😁",
                    text = "Muito bem",
                    isSelected = selectedMood == "Muito bem",
                    onClick = { selectedMood = "Muito bem" }
                )

                CheckInButton(
                    emoji = "🙂",
                    text = "Bem",
                    isSelected = selectedMood == "Bem",
                    onClick = { selectedMood = "Bem" }
                )

                CheckInButton(
                    emoji = "😐",
                    text = "Neutro",
                    isSelected = selectedMood == "Neutro",
                    onClick = { selectedMood = "Neutro" }
                )

                CheckInButton(
                    emoji = "🙁",
                    text = "Mal",
                    isSelected = selectedMood == "Mal",
                    onClick = { selectedMood = "Mal" }
                )

                CheckInButton(
                    emoji = "😠",
                    text = "Muito mal",
                    isSelected = selectedMood == "Muito mal",
                    onClick = { selectedMood = "Muito mal" }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { onCheckInClick() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF19BFB7)),
                enabled = selectedMood != null && isCheckInEnabled
            ) {
                Text(
                    text = "Check-in",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    fontFamily = ZeniteFonts.Quicksand
                )
            }
        }
    }
} 

@Composable
fun CheckInButton(
    emoji: String, 
    text: String, 
    emojiSize: Dp = 34.dp,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (isSelected) Color(0xFF19BFB7) else Color.Transparent,
                    shape = CircleShape
                )
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emoji,
                fontSize = emojiSize.value.sp
            )
        }
        
        Text(
            text = text,
            color = if (isSelected) Color(0xFF19BFB7) else White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            fontFamily = ZeniteFonts.Quicksand
        )
    }
}