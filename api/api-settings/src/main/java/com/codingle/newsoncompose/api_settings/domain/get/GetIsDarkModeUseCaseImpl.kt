package com.codingle.newsoncompose.api_settings.domain.get

import com.codingle.newsoncompose.api_settings.data.repository.SettingRepository
import javax.inject.Inject

class GetIsDarkModeUseCaseImpl @Inject constructor(
    private val repo: SettingRepository
) : GetIsDarkModeUseCase {
    override fun invoke() = repo.isDarkMode()
}
