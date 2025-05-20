package com.example.zenite.ui.components.drawer

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.zenite.R

sealed class DrawerMenuItem(
    @StringRes val titleRes: Int,
    val icon: ImageVector,
    val route: String,
    val fixedBottom: Boolean = false
) {
    object Home : DrawerMenuItem(R.string.menu_home, Icons.Default.Home, "home")
    object CheckIn : DrawerMenuItem(R.string.menu_checkin, Icons.Default.Check, "checkin")
    object Evolution : DrawerMenuItem(R.string.menu_evolution, Icons.Default.ShowChart, "evolution")
    object Support : DrawerMenuItem(R.string.menu_support, Icons.Default.Call, "support")
    object Mood : DrawerMenuItem(R.string.menu_mood, Icons.Default.Mood, "mood")
    object Diary : DrawerMenuItem(R.string.menu_diary, Icons.Default.DeveloperBoard, "diary")
    object Account : DrawerMenuItem(R.string.menu_account, Icons.Default.Person, "account")
    object Settings : DrawerMenuItem(R.string.menu_settings, Icons.Default.Settings, "settings", true)
    object Logout : DrawerMenuItem(R.string.menu_logout, Icons.Default.ExitToApp, "logout", true)
}
