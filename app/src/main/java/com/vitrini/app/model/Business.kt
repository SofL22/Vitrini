package com.vitrini.app.model

data class Business(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val location: String,
    val imageUrl: String = ""
)
