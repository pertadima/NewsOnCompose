package com.codingle.newsoncompose.api_search.di

import android.content.Context
import androidx.room.Room
import com.codingle.newsoncompose.api_search.data.SearchDatabase
import com.codingle.newsoncompose.api_search.data.local.datasource.SearchLocalDataSource
import com.codingle.newsoncompose.api_search.data.local.datasource.SearchLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    internal fun provideSearchDatabase(@ApplicationContext appContext: Context): SearchDatabase =
        Room.databaseBuilder(appContext, SearchDatabase::class.java, "search_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    internal fun provideSearchLocalDataSource(searchDatabase: SearchDatabase): SearchLocalDataSource =
        SearchLocalDataSourceImpl(searchDatabase)
}