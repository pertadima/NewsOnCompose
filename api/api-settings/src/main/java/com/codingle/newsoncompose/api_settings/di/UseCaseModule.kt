package com.codingle.newsoncompose.api_settings.di

import com.codingle.newsoncompose.api_settings.data.repository.SettingRepository
import com.codingle.newsoncompose.api_settings.domain.get.GetIsDarkModeUseCase
import com.codingle.newsoncompose.api_settings.domain.get.GetIsDarkModeUseCaseImpl
import com.codingle.newsoncompose.api_settings.domain.update.UpdateIsDarkModeUseCase
import com.codingle.newsoncompose.api_settings.domain.update.UpdateIsDarkModeUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    internal fun provideGetIsDarkModeUseCase(
        repository: SettingRepository
    ): GetIsDarkModeUseCase = GetIsDarkModeUseCaseImpl(repository)

    @Provides
    @Singleton
    internal fun provideUpdateIsDarkModeUseCase(
        repository: SettingRepository
    ): UpdateIsDarkModeUseCase = UpdateIsDarkModeUseCaseImpl(repository)
}