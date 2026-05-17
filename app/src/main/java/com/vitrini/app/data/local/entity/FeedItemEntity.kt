package com.vitrini.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "feed_items", indices = [Index("userId"), Index("productId"), Index("generatedAt")])
data class FeedItemEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val productId: String,
    val position: Int,
    val score: Double,
    val motive: String? = null,
    val generatedAt: Long,
    val expiresAt: Long? = null
)
