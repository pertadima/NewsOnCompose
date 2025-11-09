package com.codingle.newsoncompose.api_settings.data.repository

import com.codingle.newsoncompose.api_settings.data.local.datasource.SettingLocalDataSource
import com.codingle.newsoncompose.core_data.base.BaseRepository
import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import com.codingle.newsoncompose.core_data.data.entity.ApiResult.Loading
import com.codingle.newsoncompose.core_data.data.entity.ApiResult.Success
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val localDataSource: SettingLocalDataSource
) : BaseRepository(), SettingRepository {
    override fun isDarkMode(): Flow<ApiResult<Boolean>> = flow {
        emit(Loading)
        localDataSource.isDarkMode().collect { value ->
            emit(Success(value, false))
        }
    }.flowOn(IO)

    override fun updateIsDarkModePreference(isDarkMode: Boolean) = flow {
        emit(Loading)
        localDataSource.updateIsDarkModePreference(isDarkMode).collect { value ->
            emit(Success(value, false))
        }
    }.flowOn(IO)
}
