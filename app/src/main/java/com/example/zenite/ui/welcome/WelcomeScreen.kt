package com.example.zenite.ui.welcome

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.zenite.R
import com.example.zenite.ui.theme.Primary
import com.example.zenite.ui.theme.Secondary
import com.example.zenite.ui.theme.Tertiary
import com.example.zenite.ui.theme.White
import com.example.zenite.ui.theme.ZeniteFonts

@Composable
fun WelcomeScreen(onStartClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.65f)
                .align(Alignment.BottomCenter)
                .zIndex(0f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.sample_intro_bg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .zIndex(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = stringResource(R.string.app_name),
                        fontFamily = ZeniteFonts.Poppins,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = White
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.welcome_title),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    color = White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = ZeniteFonts.Poppins,
                    color = Secondary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.welcome_subtitle),
                    fontSize = 16.sp,
                    color = White,
                    textAlign = TextAlign.Center
                )
            }

            AnimatedGradientButton(
                text = stringResource(R.string.start_button),
                onClick = onStartClicked
            )
        }
    }
}

@Composable
fun AnimatedGradientButton(
    text: String,
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "looped-border")
    val rawOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = Float.POSITIVE_INFINITY,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "offsetX"
    )
    val offsetX = rawOffset % 1000f

    val animatedBrush = Brush.linearGradient(
        colors = listOf(Secondary, Tertiary, Primary, Secondary),
        start = Offset(offsetX, 0f),
        end = Offset(offsetX + 300f, 300f),
        tileMode = TileMode.Mirror
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(1.dp)
            .drawBehind {
                drawRoundRect(
                    brush = animatedBrush,
                    cornerRadius = CornerRadius(32.dp.toPx()),
                    style = Stroke(width = 3.dp.toPx())
                )
            }
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(32.dp),
                ambientColor = Secondary.copy(alpha = 0.3f),
                spotColor = Secondary.copy(alpha = 0.3f)
            )
            .clip(RoundedCornerShape(32.dp))
            .background(Primary)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = White,
            fontWeight = FontWeight.Bold
        )
    }
}

