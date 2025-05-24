package com.example.zenite.ui.screens.personal_evolution

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zenite.ui.layout.ZeniteScreen

@Composable
fun PersonalEvolutionScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: PersonalEvolutionViewModel = hiltViewModel()
) {
    val weeklyMoodData by viewModel.weeklyMoodData.collectAsState()
    val monthlyMoodPercentages by viewModel.monthlyMoodPercentages.collectAsState()
    val improvementPercentage by viewModel.improvementPercentage.collectAsState()
    
    ZeniteScreen(
        title = "Evolução pessoal",
        navController = navController
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Como foi sua semana?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    MoodLineChart(weeklyMoodData)
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf("S", "T", "Q", "Q", "S", "S", "D").forEach { day ->
                            Text(
                                text = day,
                                color = Color.Gray,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
            
            Text(
                text = "% De humor mensal",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF6E8DAB)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MoodBarChart(monthlyMoodPercentages)
                }
            }
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF154360)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Você melhorou $improvementPercentage% essa Semana!",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun MoodLineChart(moodData: List<Float>) {
    val moodColors = listOf(
        Color(0xFF4CAF50),
        Color(0xFF8BC34A),
        Color(0xFFFFEB3B),
        Color(0xFFFFC107),
        Color(0xFFFF9800),
        Color(0xFFF44336)
    )
    
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        val width = size.width
        val height = size.height
        val xStep = width / (moodData.size - 1)
        val maxY = height * 0.8f
        
        val path = Path()
        path.moveTo(0f, height - (moodData[0] * maxY))
        
        for (i in 1 until moodData.size) {
            val x = i * xStep
            val y = height - (moodData[i] * maxY)
            path.lineTo(x, y)
        }
        
        drawPath(
            path = path,
            color = Color(0xFF154360),
            style = Stroke(width = 3f)
        )
        
        for (i in moodData.indices) {
            val x = i * xStep
            val y = height - (moodData[i] * maxY)
            val colorIndex = (((1 - moodData[i]) * (moodColors.size - 1)).toInt())
                .coerceIn(0, moodColors.size - 1)
            
            drawCircle(
                color = moodColors[colorIndex],
                radius = 12f,
                center = Offset(x, y)
            )
        }
    }
}

@Composable
fun MoodBarChart(percentages: List<Pair<String, Int>>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        percentages.forEach { (label, percentage) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$percentage%",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height((percentage * 1.5).dp)
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                        .background(Color(0xFF22D1C4))
                )
                
                Text(
                    text = label,
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
} 