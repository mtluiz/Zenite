package com.example.zenite.ui.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.zenite.ui.components.ZeniteTopBar
import com.example.zenite.ui.components.drawer.DrawerItem
import com.example.zenite.ui.components.drawer.ZeniteDrawer
import kotlinx.coroutines.launch

@Composable
fun ZeniteScreen(
    title: String,
    avatarUrl: String,
    navController: NavController = rememberNavController(),
    onAvatarClick: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val drawerItems = remember { getDrawerItems() }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ZeniteDrawer(
                items = drawerItems,
                onItemClick = {
                    scope.launch { drawerState.close() }
                    navController.navigate(it.route)
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
                    onAvatarClick = onAvatarClick
                )
            },
            content = content
        )
    }
}
