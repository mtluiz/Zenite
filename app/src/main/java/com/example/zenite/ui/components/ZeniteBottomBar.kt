package com.example.zenite.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun ZeniteBottomBar(
    currentRoute: String,
    onItemClick: (String) -> Unit
) {
    val items = listOf(
        ZeniteNavItem("evolution", Icons.Default.ShowChart),
        ZeniteNavItem("check_in", Icons.Default.Check),
        ZeniteNavItem("home", Icons.Default.Home),
        ZeniteNavItem("support", Icons.Default.Call),
        ZeniteNavItem("settings", Icons.Default.Settings)
    )

    val density = LocalDensity.current
    val cutoutRadiusPx = with(density) { 50.dp.toPx() }
    val cutoutWidthPx = with(density) { 80.dp.toPx() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {
        Surface(
            color = Color.White,
            shadowElevation = 10.dp,
            shape = BottomBarCutoutShape(cutoutRadiusPx, cutoutWidthPx),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                items.take(2).forEach {
                    ZeniteBottomBarIcon(it, currentRoute, onItemClick)
                }

                Spacer(modifier = Modifier.width(48.dp))

                items.takeLast(2).forEach {
                    ZeniteBottomBarIcon(it, currentRoute, onItemClick)
                }
            }
        }

        FloatingActionButton(
            onClick = { onItemClick("home") },
            containerColor = Color(0xFF154360),
            contentColor = Color(0xFF22D1C4),
            elevation = FloatingActionButtonDefaults.elevation(12.dp),
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(72.dp)
        ) {
            Icon(Icons.Default.Home, contentDescription = "Home")
        }
    }
}

@Composable
private fun ZeniteBottomBarIcon(
    item: ZeniteNavItem,
    currentRoute: String,
    onClick: (String) -> Unit
) {
    val isSelected = currentRoute == item.route
    val tint by animateColorAsState(
        if (isSelected) Color(0xFF22D1C4) else Color.Black,
        label = "tint"
    )

    IconButton(onClick = { onClick(item.route) }) {
        Icon(item.icon, contentDescription = item.route, tint = tint)
    }
}

data class ZeniteNavItem(val route: String, val icon: ImageVector)
