package com.codingle.newsoncompose.api_settings.di

import android.content.Context
import com.codingle.newsoncompose.api_settings.data.preference.SettingPreferenceManager
import com.codingle.newsoncompose.api_settings.data.preference.SettingPreferenceManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferenceModule {

    @Provides
    @Singleton
    internal fun provideSettingPreferenceManager(
        @ApplicationContext appContext: Context
    ): SettingPreferenceManager = SettingPreferenceManagerImpl(appContext)
}