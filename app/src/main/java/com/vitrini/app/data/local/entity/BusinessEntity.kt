package com.vitrini.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(tableName = "businesses", indices = [Index("category"), Index("location")])
data class BusinessEntity(
    @PrimaryKey
    val id: String,
    val ownerUserId: String,
    val ownerName: String,
    val name: String,
    val category: String,
    val location: String,
    val imageUrl: String = ""
)
