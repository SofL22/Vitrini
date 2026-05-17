package com.vitrini.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(tableName = "users", indices = [Index("mail")])
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val mail: String,
    val profilePicUrl: String? = null,
    val userType: String = "CUSTOMER",
    val location: String? = null,
    val status: String = "ACTIVE",
    val createdAt: Long,
    val updatedAt: Long
)

