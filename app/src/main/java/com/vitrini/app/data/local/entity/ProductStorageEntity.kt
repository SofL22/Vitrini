package com.vitrini.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "products", indices = [Index("businessId"), Index("categoryId"), Index("status")])
data class ProductStorageEntity(
    @PrimaryKey val id: String,
    val businessId: String,
    val name: String,
    val description: String,
    val price: Double,
    val currency: String = "USD",
    val categoryId: String,
    val stock: Int = 0,
    val status: String = "ACTIVE",
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Long? = null
)
