package com.codingle.newsoncompose.api_search.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codingle.newsoncompose.api_search.data.dto.SearchKeywordDto

@Entity(tableName = "tbl_keyword")
data class SearchKeywordEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var title: Long = 0L,
    @ColumnInfo(name = "keyword") var keyword: String = ""
) {
    fun mapToDto() = SearchKeywordDto(keyword)
}