package com.vitrini.app.data.dto.request

data class CustomerRegisterRequestDto(
    val fullName: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)
