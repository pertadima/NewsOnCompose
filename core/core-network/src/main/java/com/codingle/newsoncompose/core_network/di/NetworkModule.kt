package com.codingle.newsoncompose.core_network.di

import android.app.Application
import com.codingle.core_network.BuildConfig.BASE_URL
import com.codingle.newsoncompose.core_network.interceptor.CommonHeaderInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpCache(@ApplicationContext application: Application): Cache {
        val cacheSize = (CACHE * BYTE_MULTIPLY * BIT_MULTIPLY).toLong()
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideCommonHeaderInterceptor() = CommonHeaderInterceptor()

    @Provides
    @Singleton
    fun provideConnectionPool() = ConnectionPool(TIMEOUT, TIMEOUT.toLong(), MILLISECONDS)

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(connectionPool: ConnectionPool): OkHttpClient.Builder = OkHttpClient().newBuilder()
        .connectionPool(connectionPool)
        .connectTimeout(TIMEOUT.toLong(), MILLISECONDS)
        .writeTimeout(TIMEOUT.toLong(), MILLISECONDS)
        .readTimeout(TIMEOUT.toLong(), MILLISECONDS)

    @Provides
    @Singleton
    fun provideOkHttpClient(okhttpClientBuilder: OkHttpClient.Builder, commonHeader: CommonHeaderInterceptor) =
        okhttpClientBuilder.apply {
            addInterceptor(commonHeader)
            addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(BODY)
            })
        }.build()

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    companion object {
        private const val CACHE = 50
        private const val BYTE_MULTIPLY = 1024
        private const val BIT_MULTIPLY = 1024
        private const val TIMEOUT = 60000
    }
}