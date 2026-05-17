package com.vitrini.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "product_media", indices = [Index("productId")])
data class ProductMediaEntity(
    @PrimaryKey val id: String,
    val productId: String,
    val type: String,
    val remoteUrl: String? = null,
    val thumbnailUrl: String? = null,
    val localPath: String? = null,
    val resourceName: String? = null,
    val order: Int = 0,
    val isPrincipal: Boolean = false,
    val lastingMs: Long? = null,
    val format: String? = null,
    val createdAt: Long,
    val updatedAt: Long
)
