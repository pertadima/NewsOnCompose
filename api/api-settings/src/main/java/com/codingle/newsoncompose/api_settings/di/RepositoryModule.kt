package com.codingle.newsoncompose.api_settings.di

import com.codingle.newsoncompose.api_settings.data.local.datasource.SettingLocalDataSource
import com.codingle.newsoncompose.api_settings.data.repository.SettingRepository
import com.codingle.newsoncompose.api_settings.data.repository.SettingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    internal fun provideSettingRepository(
        localDataSource: SettingLocalDataSource,
    ): SettingRepository = SettingRepositoryImpl(localDataSource)
}