package com.vitrini.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "product_interactions", indices = [Index("userId"), Index("productId"), Index("type"), Index("synced"), Index("createdAt")])
data class ProductInteractionEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val productId: String,
    val type: String,
    val value: String? = null,
    val lastingMs: Long? = null,
    val createdAt: Long,
    val synced: Boolean = false
)
