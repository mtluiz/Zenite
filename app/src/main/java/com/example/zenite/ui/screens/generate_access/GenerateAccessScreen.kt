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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            PasswordInputField(
                label = stringResource(id = R.string.password_label),
                password = password,
                onPasswordChange = viewModel::onPasswordChange
            )

            Spacer(modifier = Modifier.height(12.dp))

            PasswordInputField(
                label = stringResource(id = R.string.repeat_password_label),
                password = repeatPassword,
                onPasswordChange = viewModel::onRepeatPasswordChange
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (viewModel.isValid()) onGenerateCode()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF22D1C4))
            ) {
                Text(
                    text = stringResource(id = R.string.generate_code_button),
                    color = Color.White
                )
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
        }

        ZeniteFooter()
    }
}