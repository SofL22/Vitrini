package com.vitrini.app.data.dto.request

data class BusinessRegisterRequestDto(
    val ownerName: String,
    val businessName: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val phoneNumber: String? = null,
    val businessCategory: String? = null
)
