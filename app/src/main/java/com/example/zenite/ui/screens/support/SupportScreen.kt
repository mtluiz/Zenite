package com.example.zenite.ui.screens.support

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zenite.ui.layout.ZeniteScreen
import com.example.zenite.ui.theme.GrayBlue
import com.example.zenite.ui.theme.LightBlue
import com.example.zenite.ui.theme.Primary
import com.example.zenite.ui.theme.White

@Composable
fun SupportScreen() {
    var selectedSupportType by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    val supportTypes = listOf("RH", "Psicólogo")

    ZeniteScreen(title = "Suporte") { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Como podemos te ajudar?",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Selecione o tipo de suporte:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    supportTypes.forEach { type ->
                        Button(
                            onClick = { selectedSupportType = type },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedSupportType == type) Primary else LightBlue
                            )
                        ) {
                            Text(type)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Escreva sua mensagem:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(vertical = 8.dp),
                    placeholder = { Text("Digite sua mensagem aqui...") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = GrayBlue
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (selectedSupportType.isNotEmpty() && message.isNotBlank()) {
                            showSuccessDialog = true
                            selectedSupportType = ""
                            message = ""
                        } else {
                            showErrorDialog = true
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF19BFB7))
                ) {
                    Text("Enviar", color = White)
                }
            }
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Mensagem Enviada") },
            text = { Text("Sua solicitação de suporte foi enviada com sucesso.") },
            confirmButton = {
                Button(
                    onClick = { showSuccessDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF19BFB7))
                ) {
                    Text("OK")
                }
            }
        )
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Erro") },
            text = { Text("Por favor, selecione um tipo de suporte e escreva uma mensagem.") },
            confirmButton = {
                Button(
                    onClick = { showErrorDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF19BFB7))
                ) {
                    Text("OK")
                }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SupportScreenPreview() {
    SupportScreen()
}
