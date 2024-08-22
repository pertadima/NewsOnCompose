package com.codingle.newsoncompose.api_headlines.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker.Result.failure
import androidx.work.ListenableWorker.Result.success
import androidx.work.WorkerParameters
import com.codingle.newsoncompose.api_headlines.domain.get.GetRemoteHeadlineUseCase
import com.codingle.newsoncompose.core_data.data.entity.ApiResult.Success
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.distinctUntilChanged

@HiltWorker
class NewsSyncWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val getRemoteHeadlineUseCase: GetRemoteHeadlineUseCase
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        try {
            var finalResult: Result = failure()
            getRemoteHeadlineUseCase().distinctUntilChanged().collect {
                finalResult = if (it is Success) success() else failure()
            }
            return finalResult
        } catch (ex: Exception) {
            return failure()
        }
    }
}