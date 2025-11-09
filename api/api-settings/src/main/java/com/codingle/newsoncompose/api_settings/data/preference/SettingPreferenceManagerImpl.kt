package com.codingle.newsoncompose.api_settings.data.preference

import android.content.Context
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferenceManagerImpl(private val context: Context) : SettingPreferenceManager {

    private val Context.dataStore by dataStore(
        fileName = FILE_NAME,
        serializer = SettingSerializer,
    )

    override suspend fun updateIsDarkModePreference(isDarkMode: Boolean): Flow<Boolean> {
        context.dataStore.updateData { user ->
            user.toBuilder().setIsDarkMode(isDarkMode).build()
        }
        return context.dataStore.data.map { it.isDarkMode }
    }

    override suspend fun isDarkMode() = context.dataStore.data.map { it.isDarkMode }

    companion object {
        private const val FILE_NAME = "user_setting_prefs"
    }
}