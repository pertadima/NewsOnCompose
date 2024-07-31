package com.codingle.newsoncompose.api_headlines.di

import android.content.Context
import androidx.room.Room
import com.codingle.newsoncompose.api_headlines.data.local.HeadlineDatabase
import com.codingle.newsoncompose.api_headlines.data.local.datasource.HeadlineLocalDataSource
import com.codingle.newsoncompose.api_headlines.data.local.datasource.HeadlineLocalDataSourceImpl
import com.codingle.newsoncompose.api_headlines.data.remote.api.HeadlineApi
import com.codingle.newsoncompose.api_headlines.data.remote.datasource.HeadlineRemoteDataSource
import com.codingle.newsoncompose.api_headlines.data.remote.datasource.HeadlineRemoteDataSourceImpl
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
    internal fun provideSourcesDatabase(@ApplicationContext appContext: Context): HeadlineDatabase =
        Room.databaseBuilder(appContext, HeadlineDatabase::class.java, "headline_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    internal fun provideHeadlinesApi(retrofit: Retrofit): HeadlineApi = retrofit.create(HeadlineApi::class.java)

    @Provides
    @Singleton
    internal fun provideHeadlinesLocalDataSource(headlineDatabase: HeadlineDatabase): HeadlineLocalDataSource =
        HeadlineLocalDataSourceImpl(headlineDatabase)

    @Provides
    @Singleton
    internal fun provideHeadlinesRemoteDataSource(api: HeadlineApi): HeadlineRemoteDataSource =
        HeadlineRemoteDataSourceImpl(api)
}