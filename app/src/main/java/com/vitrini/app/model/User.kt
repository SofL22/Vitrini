package com.vitrini.app.model

data class User(
    val id: Int,
    val name: String,
    val mail: String,
    val password: String,
    val interests: List<String> = emptyList()
)
