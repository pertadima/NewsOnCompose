package com.codingle.newsoncompose.core.di

import android.content.Context
import com.codingle.newsoncompose.core.cache.ImageCacheHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {
    @Singleton
    @Provides
    fun providesImageCacheHelper(@ApplicationContext context: Context) = ImageCacheHelper(context)
}