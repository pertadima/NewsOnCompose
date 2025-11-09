package com.codingle.newsoncompose.api_headlines.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.codingle.newsoncompose.api_headlines.data.entity.HeadlineArticleEntity

@Dao
interface HeadlineDao {
    @Query("SELECT * FROM tbl_headline ORDER BY publishedAt DESC")
    suspend fun getAllHeadlines(): List<HeadlineArticleEntity>?

    @Query("SELECT * FROM tbl_headline WHERE source = :newsSource ORDER BY publishedAt DESC")
    suspend fun getHeadlines(newsSource: String): List<HeadlineArticleEntity>?

    @Insert(onConflict = REPLACE)
    suspend fun insertAllHeadline(sources: List<HeadlineArticleEntity>)

    @Query(
        "SELECT * FROM tbl_headline WHERE title LIKE '%' || :query || '%' OR source LIKE '%' || " +
                ":query || '%' ORDER BY publishedAt DESC"
    )
    suspend fun searchHeadline(query: String): List<HeadlineArticleEntity>?

    @Query("SELECT * from tbl_headline WHERE isFavorite = 1")
    suspend fun getFavoriteHeadlines(): List<HeadlineArticleEntity>?

    @Query("UPDATE tbl_headline SET isFavorite = :isFavorite WHERE title = :title")
    suspend fun updateIsFavoriteHeadline(isFavorite: Boolean, title: String)
}