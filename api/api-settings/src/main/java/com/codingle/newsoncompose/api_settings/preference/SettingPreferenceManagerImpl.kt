package com.codingle.newsoncompose.api_settings.preference

import android.content.Context
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferenceManagerImpl(private val context: Context) : SettingPreferenceManager {

    private val Context.dataStore by dataStore(
        fileName = "user_setting_prefs",
        serializer = SettingSerializer,
    )

    override suspend fun updateIsDarkModePreference(isDarkMode: Boolean): Flow<Boolean> {
        context.dataStore.updateData { user ->
            user.toBuilder().setIsDarkMode(isDarkMode).build()
        }
        return context.dataStore.data.map { it.isDarkMode }
    }

    override suspend fun isDarkMode() = context.dataStore.data.map { it.isDarkMode }
}