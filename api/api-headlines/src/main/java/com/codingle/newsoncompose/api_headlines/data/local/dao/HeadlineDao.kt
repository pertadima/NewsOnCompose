package com.codingle.newsoncompose.api_headlines.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.codingle.newsoncompose.api_headlines.data.entity.HeadlineArticleEntity

@Dao
interface HeadlineDao {
    @Query("SELECT * FROM tbl_headline")
    suspend fun getAllHeadlines(): List<HeadlineArticleEntity>?

    @Query("SELECT * FROM tbl_headline WHERE source = :newsSource")
    suspend fun getHeadlines(newsSource: String): List<HeadlineArticleEntity>?

    @Insert(onConflict = REPLACE)
    suspend fun insertAllHeadline(sources: List<HeadlineArticleEntity>)
}