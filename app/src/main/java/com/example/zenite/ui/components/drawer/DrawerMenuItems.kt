package com.example.zenite.ui.components.drawer

import com.example.zenite.ui.components.drawer.DrawerMenuItem.Account
import com.example.zenite.ui.components.drawer.DrawerMenuItem.CheckIn
import com.example.zenite.ui.components.drawer.DrawerMenuItem.Diary
import com.example.zenite.ui.components.drawer.DrawerMenuItem.Evolution
import com.example.zenite.ui.components.drawer.DrawerMenuItem.Home
import com.example.zenite.ui.components.drawer.DrawerMenuItem.Logout
import com.example.zenite.ui.components.drawer.DrawerMenuItem.Mood
import com.example.zenite.ui.components.drawer.DrawerMenuItem.Settings
import com.example.zenite.ui.components.drawer.DrawerMenuItem.Support

fun getDrawerMenuItems(): List<DrawerMenuItem> = listOf(
    Home,
    CheckIn,
    Evolution,
    Support,
    Mood,
    Diary,
    Account,
    Settings,
    Logout
)