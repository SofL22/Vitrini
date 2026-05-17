package com.vitrini.app.data.dto.response

data class UserResponseDto(
    val id: String,
    val name: String,
    val email: String,
    val profilePicUrl: String?,
    val userType: String,
    val location: String?,
    val status: String
)
