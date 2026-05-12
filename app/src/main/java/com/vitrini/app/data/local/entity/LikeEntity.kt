package com.vitrini.app.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "likes",
    primaryKeys = ["userId", "productId"]
)

data class LikeEntity(
    val userId: Int,
    val productId: Int
)
