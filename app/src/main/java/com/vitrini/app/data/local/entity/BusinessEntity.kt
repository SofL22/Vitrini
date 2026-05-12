package com.vitrini.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "businesses")
data class BusinessEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val location: String,
    val imageUrl: String = ""
)
