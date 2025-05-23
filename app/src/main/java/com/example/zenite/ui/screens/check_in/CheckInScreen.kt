package com.example.zenite.ui.screens.check_in

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.zenite.R
import com.example.zenite.ui.layout.ZeniteScreen
import com.example.zenite.ui.theme.Primary
import com.example.zenite.ui.theme.White
import com.example.zenite.ui.theme.Blueberry
import com.example.zenite.ui.theme.LightBlueberry
import com.example.zenite.ui.theme.ZeniteFonts
import com.example.zenite.ui.screens.check_in.CheckInViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CheckInScreen(
    viewModel: CheckInViewModel = hiltViewModel(),
    navController: NavController,
    onCheckInComplete: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    
    ZeniteScreen(title = "Check-in", navController = navController) { padding ->
        Column(
            modifier = Modifier
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Informações do local atual
            CurrentMoodCard(
                locationName = uiState.locationName,
                address = uiState.address
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                    modifier = Modifier.padding(horizontal = 25.dp),
                    text = "Check-in disponivel",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    fontFamily = ZeniteFonts.Quicksand
                )
            
            // Informações da hora
            TimeInfoCard()
        }
    }
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
    var selectedDay by remember { mutableStateOf(0) }
    
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
fun TimeInfoCard() { 
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
                )

                CheckInButton(
                    emoji = "🙂",
                    text = "Bem"
                )

                CheckInButton(
                    emoji = "😐",
                    text = "Neutro"
                )

                CheckInButton(
                    emoji = "🙁",
                    text = "Mal"
                )

                CheckInButton(
                    emoji = "😠",
                    text = "Muito mal",
                )
            }
        }
    }
} 

@Composable
fun CheckInButton(emoji: String, text: String, emojiSize: Dp = 34.dp) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emoji,
                fontSize = emojiSize.value.sp
            )
        }
        
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            fontFamily = ZeniteFonts.Quicksand
        )
    }
}