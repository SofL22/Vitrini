package com.vitrini.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String? = null,
    val status: String = "ACTIVE",
    val createdAt: Long,
    val updatedAt: Long
)
