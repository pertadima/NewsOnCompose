package com.codingle.newsoncompose.api_headlines.domain.get

import com.codingle.newsoncompose.api_headlines.data.repository.HeadlineRepository
import javax.inject.Inject

class GetFavoriteHeadlineUseCaseImpl @Inject constructor(
    private val repo: HeadlineRepository
) : GetFavoriteHeadlineUseCase {
    override fun invoke() = repo.getFavoriteHeadlines()
}
