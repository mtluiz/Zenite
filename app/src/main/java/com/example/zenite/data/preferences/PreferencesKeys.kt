package com.example.zenite.data.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val WELCOME_SEEN = booleanPreferencesKey("welcome_seen")
    val USER_CODE = stringPreferencesKey("user_code")
}
