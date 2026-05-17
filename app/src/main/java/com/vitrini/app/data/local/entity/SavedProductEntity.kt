package com.vitrini.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index

@Entity(tableName = "saved_products", primaryKeys = ["userId", "productId"], indices = [Index("userId"), Index("productId")])
data class SavedProductEntity(
    val userId: String,
    val productId: String,
    val createdAt: Long,
    val synced: Boolean = false
)
