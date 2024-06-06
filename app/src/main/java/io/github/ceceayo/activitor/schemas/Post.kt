package io.github.ceceayo.activitor.schemas

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Post(
    @PrimaryKey val id: String,
    @ColumnInfo(name="contents") val content: String,
    @ColumnInfo(name="created_at") val created_at: String
)
