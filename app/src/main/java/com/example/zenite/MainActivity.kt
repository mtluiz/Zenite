package com.example.zenite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.zenite.navigation.ZeniteHomePage
import com.example.zenite.ui.screens.splash.SplashScreen
import com.example.zenite.ui.screens.welcome.WelcomeScreen
import com.example.zenite.ui.screens.welcome.WelcomeState
import com.example.zenite.ui.screens.welcome.WelcomeViewModel
import com.example.zenite.ui.theme.ZeniteTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: WelcomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZeniteTheme {
                var splashDone by remember { mutableStateOf(false) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when {
                        !splashDone -> SplashScreen {
                            splashDone = true
                        }

                        else -> when (val state = viewModel.state.value) {
                            WelcomeState.Loading -> Unit

                            WelcomeState.ShowWelcome -> WelcomeScreen {
                                viewModel.onWelcomeDone()
                            }

                            WelcomeState.NavigateHome -> {
                                ZeniteHomePage()
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    ZeniteTheme {
//        ZeniteHomePage()
//    WelcomeScreen {  }
//        LoginScreen(
//            onLoginClick = {},
//            onForgotPassword = {},
//            onGenerateAccess = {}
//        )
//        GenerateAccessScreen(onBack = {}, onAccessAccount = {}, onGenerateCode = {})
    }
}