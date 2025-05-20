package com.example.zenite.ui.components.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ZeniteDrawer(
    items: List<DrawerItem>,
    onItemClick: (DrawerItem) -> Unit,
    onClose: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Fechar menu"
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(text = "Voltar", style = MaterialTheme.typography.titleMedium)
        }

        Divider()

        items.forEach { item ->
            NavigationDrawerItem(
                label = { Text(text = item.label) },
                selected = false,
                onClick = { onItemClick(item) },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.label)
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}
