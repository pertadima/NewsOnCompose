package com.codingle.core_network.di

import android.app.Application
import com.codingle.core_network.interceptor.CommonHeaderInterceptor
import com.codingle.network.BuildConfig.BASE_URL
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
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
            hostnameVerifier { _, _ -> true }
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(
        moshi: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(moshi))
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