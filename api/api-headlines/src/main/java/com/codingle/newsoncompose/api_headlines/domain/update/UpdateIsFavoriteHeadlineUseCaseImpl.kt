package com.codingle.newsoncompose.api_headlines.domain.update

import com.codingle.newsoncompose.api_headlines.data.repository.HeadlineRepository
import javax.inject.Inject

class UpdateIsFavoriteHeadlineUseCaseImpl @Inject constructor(
    private val repo: HeadlineRepository
) : UpdateIsFavoriteHeadlineUseCase {
    override fun invoke(
        isFavorite: Boolean,
        title: String
    ) = repo.updateIsFavoriteHeadline(isFavorite, title)
}
