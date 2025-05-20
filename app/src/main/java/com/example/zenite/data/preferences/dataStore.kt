package com.example.zenite.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "zenite_prefs")

class UserPreferencesImpl @Inject constructor(@ApplicationContext private val context: Context) :
    UserPreferences {

    override suspend fun setWelcomeSeen(seen: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.WELCOME_SEEN] = seen
        }
    }

    override suspend fun hasSeenWelcome(): Boolean {
        return context.dataStore.data
            .map { prefs -> prefs[PreferencesKeys.WELCOME_SEEN] == true }
            .first()
    }
}
