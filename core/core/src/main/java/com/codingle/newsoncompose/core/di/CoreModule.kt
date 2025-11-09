package com.codingle.newsoncompose.core.di

import com.codingle.newsoncompose.core.cache.ImageCacheHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {
    @Singleton
    @Provides
    fun providesImageCacheHelper() = ImageCacheHelper()
}