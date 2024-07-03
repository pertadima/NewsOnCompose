package com.codingle.newsoncompose.api_sources.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.codingle.newsoncompose.api_sources.data.entity.SourceEntity

@Dao
interface SourcesDao {
    @Query("SELECT * FROM tbl_sources")
    suspend fun getAllSources(): List<SourceEntity>?

    @Insert(onConflict = REPLACE)
    suspend fun insertAllSources(sources: List<SourceEntity>)
}