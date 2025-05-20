package com.example.zenite.data.preferences

interface UserPreferences {
    suspend fun setWelcomeSeen(seen: Boolean)
    suspend fun hasSeenWelcome(): Boolean
}
