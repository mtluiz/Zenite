package com.example.zenite.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zenite.R
import com.example.zenite.ui.layout.ZeniteScreen
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun MudarAvatarScreen(
    viewModel: MudarAvatarViewModel = viewModel(),
    onAvatarSelected: (Int) -> Unit
) {
    val selectedAvatar by viewModel.selectedAvatar.collectAsState()

    ZeniteScreen(title = stringResource(R.string.mudar_avatar)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf(R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3).forEach { avatar ->
                    Image(
                        painter = painterResource(id = avatar),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clickable { viewModel.selectAvatar(avatar) }
                            .border(
                                width = if (selectedAvatar == avatar) 2.dp else 0.dp,
                                color = if (selectedAvatar == avatar) MaterialTheme.colorScheme.primary else Color.Transparent
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    selectedAvatar?.let { onAvatarSelected(it) }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(R.string.salvar))
            }
        }
    }
}


