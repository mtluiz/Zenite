package com.example.zenite.ui.screens.avatars

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.zenite.R
import com.example.zenite.ui.components.ZeniteFooter

@Composable
fun ChooseAvatarScreen(
    avatars: List<String>,
    onAvatarSelected: (String) -> Unit,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(id = R.string.choose_avatar_title),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(avatars.chunked(2)) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { avatarPath ->
                        AvatarOption(avatarPath = avatarPath) {
                            onAvatarSelected(avatarPath)
                        }
                    }

                    if (row.size < 2) Spacer(modifier = Modifier.size(120.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSaveClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF22D1C4))
        ) {
            Text(text = stringResource(id = R.string.save_button), color = Color.White)
        }

        ZeniteFooter()
    }
}

@Composable
fun AvatarOption(avatarPath: String, onClick: () -> Unit) {
    Image(
        painter = rememberAsyncImagePainter(avatarPath),
        contentDescription = null,
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .clickable { onClick() }
    )
}
