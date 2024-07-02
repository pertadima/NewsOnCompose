package com.codingle.newsoncompose.api_sources.di

import android.content.Context
import androidx.room.Room
import com.codingle.newsoncompose.api_sources.data.local.SourcesDatabase
import com.codingle.newsoncompose.api_sources.data.local.datasource.SourcesLocalDataSource
import com.codingle.newsoncompose.api_sources.data.local.datasource.SourcesLocalDataSourceImpl
import com.codingle.newsoncompose.api_sources.data.remote.api.SourcesApi
import com.codingle.newsoncompose.api_sources.data.remote.datasource.SourcesRemoteDataSource
import com.codingle.newsoncompose.api_sources.data.remote.datasource.SourcesRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    internal fun provideSourcesDatabase(@ApplicationContext appContext: Context): SourcesDatabase =
        Room.databaseBuilder(appContext, SourcesDatabase::class.java, "sources_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    internal fun provideSourcesApi(retrofit: Retrofit): SourcesApi = retrofit.create(SourcesApi::class.java)

    @Provides
    @Singleton
    internal fun provideSourcesLocalDataSource(sourcesDatabase: SourcesDatabase): SourcesLocalDataSource =
        SourcesLocalDataSourceImpl(sourcesDatabase)

    @Provides
    @Singleton
    internal fun provideSourcesRemoteDataSource(api: SourcesApi): SourcesRemoteDataSource =
        SourcesRemoteDataSourceImpl(api)
}