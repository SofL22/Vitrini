package com.vitrini.app.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val id: Int,
    val businessId: Int,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String = ""
)
