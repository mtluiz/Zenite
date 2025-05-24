package com.example.zenite.ui.screens.generate_access

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zenite.R
import com.example.zenite.ui.components.EditableAvatar
import com.example.zenite.ui.components.PasswordInputField
import com.example.zenite.ui.components.ZeniteFooter
import com.example.zenite.ui.screens.avatars.AvatarViewModel

@Composable
fun GenerateAccessScreen(
    onBack: () -> Unit,
    onAccessAccount: () -> Unit,
    onGenerateCode: () -> Unit,
    onOpenAvatarScreen: () -> Unit,
    viewModel: GenerateAccessViewModel = hiltViewModel()
) {
    val avatarViewModel: AvatarViewModel = hiltViewModel()
    val avatarUrl by avatarViewModel.avatarUrl.collectAsState()

    val fullName by viewModel.fullName.collectAsState()
    val password by viewModel.password.collectAsState()
    val repeatPassword by viewModel.repeatPassword.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val userCode by viewModel.userCode.collectAsState()
    
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    
    LaunchedEffect(userCode) {
        if (userCode != null) {
            onGenerateCode()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color(0xFF22D1C4)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.welcome),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            EditableAvatar(imageUrl = avatarUrl, onClick = onOpenAvatarScreen)

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.generate_access_title),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = fullName,
                onValueChange = viewModel::onFullNameChange,
                label = { Text(stringResource(id = R.string.full_name)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(12.dp))

            PasswordInputField(
                label = stringResource(id = R.string.password_label),
                password = password,
                onPasswordChange = viewModel::onPasswordChange,
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(12.dp))

            PasswordInputField(
                label = stringResource(id = R.string.repeat_password_label),
                password = repeatPassword,
                onPasswordChange = viewModel::onRepeatPasswordChange,
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.register() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF22D1C4)),
                enabled = !isLoading && viewModel.isValid()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                Text(
                    text = stringResource(id = R.string.generate_code_button),
                    color = Color.White
                )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.Center) {
                Text(text = stringResource(id = R.string.have_login))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(id = R.string.access_account),
                    color = Color(0xFF22D1C4),
                    modifier = Modifier.clickable { onAccessAccount() }
                )
            }
            
            // Exibir mensagem de erro se houver
            errorMessage?.let { error ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = error,
                    color = Color.Red,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        ZeniteFooter()
    }
    
    if (userCode != null) {
        var showDialog by remember { mutableStateOf(true) }
        
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Seu código foi gerado!") },
                text = { 
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Anote este código para acessar sua conta:")
                        Spacer(modifier = Modifier.height(16.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = userCode ?: "",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                IconButton(onClick = {
                                    clipboardManager.setText(AnnotatedString(userCode ?: ""))
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Copiar código"
                                    )
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Entendi")
                    }
                }
            )
        }
    }
}