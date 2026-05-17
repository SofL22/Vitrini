package com.vitrini.app.data.dto.response

data class AuthResponseDto(
    val authToken: String,
    val userId: String,
    val accountType: String,
    val user: UserResponseDto? = null,
    val business: BusinessResponseDto? = null
)
