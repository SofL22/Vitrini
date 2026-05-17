package com.vitrini.app.auth

class AuthService {
    fun isTokenValid(token: String?): Boolean = !token.isNullOrBlank()
}