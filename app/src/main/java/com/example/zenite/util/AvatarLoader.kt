package com.example.zenite.util

import android.content.Context
import java.io.IOException

fun getRandomAvatar(context: Context): String? {
    return try {
        val files = context.assets.list("avatars") ?: return null
        if (files.isNotEmpty()) {
            "file:///android_asset/avatars/${files.random()}"
        } else null
    } catch (_: IOException) {
        null
    }
}

fun getAllAvatars(context: Context): List<String> {
    return try {
        context.assets.list("avatars")
            ?.map { "file:///android_asset/avatars/$it" }
            ?: emptyList()
    } catch (e: IOException) {
        emptyList()
    }
}
