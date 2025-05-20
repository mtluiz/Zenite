package com.example.zenite.ui.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zenite.ui.components.ZeniteBottomBar
import com.example.zenite.ui.components.ZeniteTopBar
import com.example.zenite.ui.components.drawer.ZeniteDrawer
import com.example.zenite.ui.components.drawer.getDrawerMenuItems
import com.example.zenite.util.getRandomAvatar
import kotlinx.coroutines.launch

@Composable
fun ZeniteScreen(
    title: String,
    navController: NavHostController = rememberNavController(),
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val drawerItems = remember { getDrawerMenuItems() }

    val context = LocalContext.current
    val avatarUrl by remember {
        mutableStateOf(getRandomAvatar(context) ?: "file:///android_asset/avatars/default.png")
    }

    var currentRoute by remember { mutableStateOf("home") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ZeniteDrawer(
                items = drawerItems,
                onItemClick = {
                    scope.launch { drawerState.close() }
                    navController.navigate(it.route)
                    currentRoute = it.route
                },
                onClose = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                ZeniteTopBar(
                    title = title,
                    avatarUrl = avatarUrl,
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onAvatarClick = { /* TODO adicionar ação de clique no avatar */ }
                )
            },
            bottomBar = {
                ZeniteBottomBar(
                    currentRoute = currentRoute,
                    onItemClick = {
                        navController.navigate(it)
                        currentRoute = it
                    }
                )
            },
            content = content
        )
    }
}