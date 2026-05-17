package com.vitrini.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items", indices = [Index("userId"), Index("productId")])
data class CartItemEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val productId: String,
    val quantity: Int,
    val unitaryPrice: Double,
    val createdAt: Long,
    val updatedAt: Long,
    val synced: Boolean = false
)
