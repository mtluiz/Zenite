package com.example.zenite.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zenite.R
import com.example.zenite.ui.layout.ZeniteScreen


@Composable
fun SeusDadosScreen(
    viewModel: SeusDadosViewModel = viewModel(),
    onAvatarClick: () -> Unit
) {
    val senha by viewModel.senha.collectAsState()
    val showAlteracoesSalvas by viewModel.showAlteracoesSalvas.collectAsState()

    ZeniteScreen(title = stringResource(R.string.seus_dados)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Image(
                    painter = painterResource(R.drawable.heart),
                    contentDescription = stringResource(R.string.avatar),
                    modifier = Modifier
                        .size(100.dp)
                        .clickable { onAvatarClick() }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = "123456", // Código Zênite fixo
                onValueChange = {},
                label = { Text(stringResource(R.string.codigo_zenite)) },
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = senha,
                onValueChange = { viewModel.onSenhaChange(it) },
                label = { Text(stringResource(R.string.senha)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.salvarAlteracoes() },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(R.string.salvar))
            }
        }

        if (showAlteracoesSalvas) {
            AlteracoesSalvasOverlay { viewModel.dismissAlteracoesSalvas() }
        }
    }
}


