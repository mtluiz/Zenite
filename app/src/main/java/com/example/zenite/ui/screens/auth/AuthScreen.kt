package com.example.zenite.ui.screens.auth

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zenite.R
import com.example.zenite.ui.components.ZeniteFooter
import com.example.zenite.ui.theme.Primary
import com.example.zenite.ui.theme.Tertiary
import com.example.zenite.ui.theme.White

@Composable
fun LoginScreen(onLoginClick: () -> Unit, onForgotPassword: () -> Unit, onGenerateAccess: () -> Unit) {
    var code by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.welcome),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.login_illustration),
                contentDescription = null,
                modifier = Modifier.size(240.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.login_title),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = code,
                onValueChange = { code = it },
                label = { Text(stringResource(id = R.string.code_label)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Badge,
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(id = R.string.password_label)) },
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.forgot_password),
                color = Tertiary,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onForgotPassword() }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { onLoginClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary)
            ) {
                Text(text = stringResource(id = R.string.login_button), color = White)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.Center) {
                Text(text = stringResource(id = R.string.no_account))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(id = R.string.generate_access),
                    color = Tertiary,
                    modifier = Modifier.clickable { onGenerateAccess() }
                )
            }
        }
        ZeniteFooter()
    }
}
