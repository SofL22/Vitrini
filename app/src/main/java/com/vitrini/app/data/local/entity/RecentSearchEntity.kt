package com.vitrini.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "recent_searches", indices = [Index("userId"), Index("createdAt")])
data class RecentSearchEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val text: String,
    val createdAt: Long
)
