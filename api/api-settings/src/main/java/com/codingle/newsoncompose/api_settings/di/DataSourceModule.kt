package com.codingle.newsoncompose.api_settings.di

import com.codingle.newsoncompose.api_settings.data.local.datasource.SettingLocalDataSource
import com.codingle.newsoncompose.api_settings.data.local.datasource.SettingLocalDataSourceImpl
import com.codingle.newsoncompose.api_settings.data.preference.SettingPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    internal fun provideSettingLocalDataSource(
        settingPreferenceManager: SettingPreferenceManager
    ): SettingLocalDataSource = SettingLocalDataSourceImpl(settingPreferenceManager)
}