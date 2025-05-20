package com.example.zenite.ui.screens.demo

import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.zenite.ui.theme.Black
import com.example.zenite.ui.theme.GrayDark
import com.example.zenite.ui.theme.GrayLight
import com.example.zenite.ui.theme.Primary
import com.example.zenite.ui.theme.Secondary
import com.example.zenite.ui.theme.Tertiary

@Composable
fun ZeniteHomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ZENITE",
            style = MaterialTheme.typography.headlineLarge,
            color = Primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Welcome to your modern app",
            style = MaterialTheme.typography.titleLarge,
            color = Secondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Color showcase
        Text(
            text = "Brand Colors",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ColorSwatch(color = Primary, name = "Primary")
            ColorSwatch(color = Secondary, name = "Secondary")
            ColorSwatch(color = Tertiary, name = "Tertiary")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ColorSwatch(color = GrayLight, name = "Gray Light")
            ColorSwatch(color = GrayDark, name = "Gray Dark")
            ColorSwatch(color = Black, name = "Black")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Typography showcase
        Text(
            text = "Typography",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Quicksand Font",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Used for headings and titles",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Poppins Font",
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "Used for body text and UI elements",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ColorSwatch(color: Color, name: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(color)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )
    }
}