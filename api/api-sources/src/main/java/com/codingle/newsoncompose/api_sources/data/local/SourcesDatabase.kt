package com.codingle.newsoncompose.api_sources.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codingle.newsoncompose.api_sources.data.entity.SourceEntity
import com.codingle.newsoncompose.api_sources.data.local.dao.SourcesDao

@Database(
    entities = [
        SourceEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SourcesDatabase : RoomDatabase() {
    abstract fun sourceDao(): SourcesDao
}