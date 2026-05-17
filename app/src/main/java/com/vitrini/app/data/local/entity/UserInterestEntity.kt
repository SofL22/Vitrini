package com.vitrini.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "user_interest",
    primaryKeys = ["userId", "categoryId"],
    indices = [Index("userId"), Index("categoryId")]
)
data class UserInterestEntity(
    val userId: String,
    val categoryId: String,
    val createdAt: Long,
    val synced: Boolean = false
)
