package com.example.zenite.ui.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zenite.R
import com.example.zenite.ui.theme.ZeniteFonts
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    val rotation = remember { Animatable(0f) }
    val bounce = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        launch {
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = FastOutSlowInEasing
                )
            )
        }

        launch {
            bounce.animateTo(
                targetValue = 1f,
                animationSpec = keyframes {
                    durationMillis = 1200
                    0.0f at 0
                    1.2f at 300
                    0.85f at 600
                    1f at 1000
                }
            )
        }

        delay(2000)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF144364)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(100.dp)
                    .graphicsLayer {
                        rotationZ = rotation.value
                    }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Zenite",
                fontFamily = ZeniteFonts.Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                color = Color.White,
                modifier = Modifier.scale(bounce.value)
            )
        }
    }
}
