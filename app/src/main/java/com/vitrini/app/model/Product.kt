package com.vitrini.app.model

data class Product(
    val id: Int,
    val businessId: Int,
    val name: String,
    val descriptionProd: String,
    val price: Double,
    val category: String,
    val imageUrl: String = ""
)
