package com.example.zenite.ui.components

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class BottomBarCutoutShape(
    private val cutoutRadius: Float,
    private val cutoutWidth: Float
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val width = size.width
        val height = size.height
        val centerX = width / 2

        val outerPath = Path().apply {
            addRect(Rect(0f, 0f, width, height))
        }

        // Ajuste: largura maior para encaixar certinho com o botão flutuante
        val cutoutPath = Path().apply {
            addRoundRect(
                RoundRect(
                    left = centerX - cutoutWidth / 2f,
                    top = -cutoutRadius,
                    right = centerX + cutoutWidth / 2f,
                    bottom = cutoutRadius,
                    cornerRadius = CornerRadius(cutoutRadius, cutoutRadius)
                )
            )
        }

        val finalPath = Path.combine(
            PathOperation.Difference,
            outerPath,
            cutoutPath
        )

        return Outline.Generic(finalPath)
    }
}


