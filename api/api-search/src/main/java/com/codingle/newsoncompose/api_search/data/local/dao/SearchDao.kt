package com.codingle.newsoncompose.api_search.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.codingle.newsoncompose.api_search.data.entity.SearchKeywordEntity

@Dao
interface SearchDao {
    @Query("SELECT * FROM tbl_keyword")
    suspend fun getAllSearchKeyword(): List<SearchKeywordEntity>?

    @Insert(onConflict = REPLACE)
    suspend fun insertKeyword(keywordEntity: SearchKeywordEntity)
}