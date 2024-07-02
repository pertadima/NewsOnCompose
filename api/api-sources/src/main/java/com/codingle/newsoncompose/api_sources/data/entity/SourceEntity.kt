package com.codingle.newsoncompose.api_sources.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codingle.newsoncompose.api_sources.data.dto.SourceDto

@Entity(tableName = "tbl_sources")
data class SourceEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") var id: String = "",
    @ColumnInfo(name = "category") var category: String = "",
    @ColumnInfo(name = "country") var country: String = "",
    @ColumnInfo(name = "description") var description: String = "",
    @ColumnInfo(name = "language") var language: String = "",
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "url") var url: String = "",
) {
    fun mapToDto() = SourceDto(
        id = id,
        category = category,
        country = country,
        description = description,
        language = language,
        name = name,
        url = url
    )
}