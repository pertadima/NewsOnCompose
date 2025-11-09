package com.codingle.newsoncompose.api_settings.domain.update

import com.codingle.newsoncompose.api_settings.data.repository.SettingRepository
import javax.inject.Inject

class UpdateIsDarkModeUseCaseImpl @Inject constructor(
    private val repo: SettingRepository
) : UpdateIsDarkModeUseCase {
    override fun invoke(isDarkMode: Boolean) = repo.updateIsDarkModePreference(isDarkMode)
}