package com.vitrini.app.data.dto.response

data class ProductResponseDto(
    val id: String,
    val businessId: String,
    val name: String,
    val description: String,
    val price: Double,
    val currency: String,
    val categoryId: String,
    val stock: Int,
    val status: String
)
