package com.codingle.newsoncompose.api_headlines.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codingle.newsoncompose.api_headlines.data.entity.HeadlineArticleEntity
import com.codingle.newsoncompose.api_headlines.data.local.dao.HeadlineDao

@Database(
    entities = [
        HeadlineArticleEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class HeadlineDatabase : RoomDatabase() {
    abstract fun headlineDao(): HeadlineDao
}