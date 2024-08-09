package com.codingle.newsoncompose.api_search.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codingle.newsoncompose.api_search.data.entity.SearchKeywordEntity
import com.codingle.newsoncompose.api_search.data.local.dao.SearchDao

@Database(
    entities = [SearchKeywordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SearchDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchDao
}