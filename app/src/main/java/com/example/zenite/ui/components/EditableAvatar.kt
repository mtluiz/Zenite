package com.example.zenite.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun EditableAvatar(
    imageUrl: String?,
    onClick: () -> Unit
) {
    Box(modifier = Modifier.size(140.dp), contentAlignment = Alignment.BottomEnd) {
        val painter = if (imageUrl != null) {
            rememberAsyncImagePainter(model = imageUrl)
        } else {
            rememberVectorPainter(Icons.Default.Person)
        }

        Image(
            painter = painter,
            contentDescription = "Avatar",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar avatar",
                tint = Color(0xFF22D1C4),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
