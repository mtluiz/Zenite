package com.example.zenite.ui.screens.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zenite.ui.layout.ZeniteScreen
import com.example.zenite.ui.theme.Black
import com.example.zenite.ui.theme.GrayDark
import com.example.zenite.ui.theme.GrayLight
import com.example.zenite.ui.theme.Primary
import com.example.zenite.ui.theme.Secondary
import com.example.zenite.ui.theme.Tertiary
import com.example.zenite.ui.theme.Blueberry
@Composable
fun ZeniteHomeScreen(
    navController: NavHostController = rememberNavController()
) {
    ZeniteScreen(title = "Home", navController = navController) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Olá, Mariana Alves",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Bem-vinda de volta",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Blueberry
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DayCircle("Seg", "😊") // Good mood
                        DayCircle("Ter", "😔") // Bad mood
                        DayCircle("Qua", "🙂") // Okay mood
                        DayCircle("Qui", "😊") // Good mood
                        DayCircle("Sex", "🙂") // Okay mood
                        DayCircle("Sab", "😔") // Bad mood
                        DayCircle("Dom", "😐") // Neutral mood
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Sentimento semanal",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color.White
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Veja também:",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            SupportCard()
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                QuestionnaireCard(
                    modifier = Modifier.weight(1f),
                    onButtonClick = { navController.navigate("questionnaire") }
                )
                MoodDiaryCard(
                    modifier = Modifier.weight(1f),
                    onButtonClick = { navController.navigate("mood_diary") }
                )
            }
        }
    }
}

@Composable
fun DayCircle(day: String, emoji: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(1.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emoji,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                color = Color.White
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = day,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}

@Composable
fun SupportCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE0F7FA)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Precisa de apoio?",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Arrow forward",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        
        Text(
            text = "Fale com a gente",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun QuestionnaireCard(modifier: Modifier = Modifier, onButtonClick: () -> Unit = {}) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE8F5E9) // Light green color
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Questionário Diário",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "Responda rápidas perguntas sobre seu dia",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )
            
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clickable { onButtonClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Iniciar",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun MoodDiaryCard(modifier: Modifier = Modifier, onButtonClick: () -> Unit = {}) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE1F5FE) // Light blue color
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Diário de Humor",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "Conte como anda seu humor hoje",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )
            
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clickable { onButtonClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Abrir",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}