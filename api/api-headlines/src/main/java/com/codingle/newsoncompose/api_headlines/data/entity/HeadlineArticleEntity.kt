package com.codingle.newsoncompose.api_headlines.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineArticleDto

@Entity(tableName = "tbl_headline")
data class HeadlineArticleEntity(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "author") var author: String = "",
    @ColumnInfo(name = "content") var content: String = "",
    @ColumnInfo(name = "description") var description: String = "",
    @ColumnInfo(name = "publishedAt") var publishedAt: String = "",
    @ColumnInfo(name = "source") var source: String = "",
    @ColumnInfo(name = "url") var url: String = "",
    @ColumnInfo(name = "urlToImage") var urlToImage: String = ""
) {
    fun mapToDto() = HeadlineArticleDto(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}