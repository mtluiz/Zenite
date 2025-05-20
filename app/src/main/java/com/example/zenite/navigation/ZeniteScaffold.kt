package com.example.zenite.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zenite.ui.components.DrawerItem
import com.example.zenite.ui.components.ZeniteDrawer
import com.example.zenite.ui.components.drawer.DrawerItem
import com.example.zenite.ui.components.drawer.ZeniteDrawer
import kotlinx.coroutines.launch

@Composable
fun ZeniteScaffold(navController: NavHostController = rememberNavController()) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val menuItems = listOf(
        DrawerItem("Home", Icons.Default.Home, "home"),
        DrawerItem("Check-in", Icons.Default.Check, "checkin"),
        DrawerItem("Evolução pessoal", Icons.Default.ShowChart, "evolution"),
        DrawerItem("Suporte", Icons.Default.Call, "support"),
        DrawerItem("Registre seu humor", Icons.Default.Mood, "mood"),
        DrawerItem("Questionário diário", Icons.Default.DeveloperBoard, "diary"),
        DrawerItem("Conta", Icons.Default.Person, "account"),
        DrawerItem("Configurações", Icons.Default.Settings, "settings"),
        DrawerItem("Sair", Icons.Default.ExitToApp, "logout")
    )

    ModalNavigationDrawer(
        drawerContent = {
            ZeniteDrawer(
                items = menuItems,
                onItemClick = {
                    scope.launch { drawerState.close() }
                    navController.navigate(it.route)
                },
                onClose = {
                    scope.launch { drawerState.close() }
                }
            )
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Zênite") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Text(
                text = "Conteúdo principal vai aqui",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
