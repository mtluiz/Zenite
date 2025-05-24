package com.example.zenite.ui.screens.check_in

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.zenite.R
import com.example.zenite.ui.layout.ZeniteScreen
import com.example.zenite.ui.theme.Blueberry
import com.example.zenite.ui.theme.White
import com.example.zenite.ui.theme.ZeniteFonts
import kotlinx.coroutines.delay

@Composable
fun CheckInScreen(
    navController: NavHostController,
    viewModel: CheckInViewModel = hiltViewModel(),
    onCheckInComplete: () -> Unit = {}
) {
    val scroll = rememberScrollState()
    val selectedMood by viewModel.selectedMood.collectAsState()
    val last3Days by viewModel.last3Days.collectAsState()
    var showSuccess by remember { mutableStateOf(false) }

    if (showSuccess) {
        LaunchedEffect(Unit) {
            delay(3000)
            showSuccess = false
            onCheckInComplete()
        }

        Dialog(onDismissRequest = { }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF22567B), shape = RoundedCornerShape(16.dp))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.popup_checkin),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        text = "Check-in realizado com sucesso!",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    ZeniteScreen(title = stringResource(R.string.checkin), navController = navController) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(scroll)
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CheckInStreak(days = last3Days, expanded = true)

            Text(
                text = "Check-in disponível",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 4.dp)
            )

            MoodSelector(
                selected = selectedMood,
                onSelect = { viewModel.selectMood(it) }
            )

            Button(
                onClick = {
                    viewModel.submitCheckin { showSuccess = true }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = selectedMood != null,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF19BFB7))
            ) {
                Text(
                    text = stringResource(R.string.checkin),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CheckInStreak(days: List<MoodDay>, expanded: Boolean = false) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (expanded) 140.dp else 100.dp),
        colors = CardDefaults.cardColors(containerColor = Blueberry),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.humor_history),
                color = White,
                fontSize = 16.sp,
                fontFamily = ZeniteFonts.Quicksand,
                fontWeight = FontWeight.SemiBold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                days.forEach { day ->
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF1B4D70)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter("file:///android_asset/faces/${day.mood ?: 3}.png"),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = day.day,
                                color = White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Image(
                            painter = painterResource(id = if (day.checked) R.drawable.check else R.drawable.cross),
                            contentDescription = null,
                            modifier = Modifier
                                .size(18.dp)
                                .align(Alignment.BottomEnd)
                                .offset(x = 12.dp, y = 12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MoodSelector(
    selected: Int?,
    onSelect: (Int) -> Unit
) {
    val moods = listOf(
        R.string.mood_very_good,
        R.string.mood_good,
        R.string.mood_neutral,
        R.string.mood_bad,
        R.string.mood_very_bad
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Blueberry),
        shape = RoundedCornerShape(40.dp)
    ) {
        Column(
            Modifier.padding(vertical = 24.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.how_you_feel),
                color = White,
                fontSize = 16.sp,
                fontFamily = ZeniteFonts.Quicksand,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                (1..5).forEach { moodIndex ->
                    val isSelected = selected == moodIndex
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable { onSelect(moodIndex) }
                            .padding(horizontal = 4.dp)
                            .width(60.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) Color.White.copy(alpha = 0.2f)
                                    else Color.Transparent
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter("file:///android_asset/faces/$moodIndex.png"),
                                contentDescription = null,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = stringResource(id = moods[moodIndex - 1]),
                            fontSize = 11.sp,
                            color = White,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            lineHeight = 12.sp,
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}