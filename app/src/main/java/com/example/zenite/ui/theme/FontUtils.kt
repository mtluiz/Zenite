package com.example.zenite.ui.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.example.zenite.R

/**
 * Utility object for managing fonts in the application.
 * Provides easy access to the Quicksand and Poppins font families.
 */
object ZeniteFonts {
    private val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    private val poppinsFont = GoogleFont("Poppins")
    private val quicksandFont = GoogleFont("Quicksand")

    val Poppins = FontFamily(
        Font(googleFont = poppinsFont, fontProvider = provider, weight = FontWeight.Normal),
        Font(googleFont = poppinsFont, fontProvider = provider, weight = FontWeight.Medium),
        Font(googleFont = poppinsFont, fontProvider = provider, weight = FontWeight.Bold)
    )

    val Quicksand = FontFamily(
        Font(googleFont = quicksandFont, fontProvider = provider, weight = FontWeight.Normal),
        Font(googleFont = quicksandFont, fontProvider = provider, weight = FontWeight.SemiBold)
    )
} 