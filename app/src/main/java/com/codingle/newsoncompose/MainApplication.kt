package com.codingle.newsoncompose

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy.UPDATE
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.codingle.newsoncompose.api_headlines.worker.NewsSyncWorker
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit.HOURS

@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface HiltWorkerFactoryEntryPoint {
        fun workerFactory(): HiltWorkerFactory
    }

    override val workManagerConfiguration = Configuration.Builder()
        .setWorkerFactory(EntryPoints.get(this, HiltWorkerFactoryEntryPoint::class.java).workerFactory())
        .build()

    override fun onCreate() {
        super.onCreate()
        scheduleNewsSyncWorker(this)
    }

    private fun scheduleNewsSyncWorker(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<NewsSyncWorker>(1, HOURS).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(WORKER_NAME, UPDATE, workRequest)
    }

    companion object {
        private const val WORKER_NAME = "NewsSyncWork"
    }
}