package com.vitrini.app.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vitrini.app.model.Like
import com.vitrini.app.model.User

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveActiveUser(user: User) {
        val json = gson.toJson(user)
        prefs.edit()
            .putString(Constants.KEY_ACTIVE_USER, json)
            .apply()
    }

    fun getActiveUser(): User? {
        val json = prefs.getString(Constants.KEY_ACTIVE_USER, null)
        return if (json != null) {
            gson.fromJson(json, User::class.java)
        } else {
            null
        }
    }

    fun isUserLoggedIn(): Boolean {
        return getActiveUser() != null || getActiveUserId() != null
    }

    fun saveActiveUserId(userId: String) {
        prefs.edit().putString(Constants.KEY_ACTIVE_USER_ID, userId).apply()
    }

    fun getActiveUserId(): String? = prefs.getString(Constants.KEY_ACTIVE_USER_ID, null)

    fun saveAuthToken(token: String) {
        prefs.edit().putString(Constants.KEY_AUTH_TOKEN, token).apply()
    }

    fun getAuthToken(): String? = prefs.getString(Constants.KEY_AUTH_TOKEN, null)

    fun logout() {
        prefs.edit()
            .remove(Constants.KEY_ACTIVE_USER)
            .apply()
    }

    fun saveLikes(likes: List<Like>) {
        val json = gson.toJson(likes)
        prefs.edit()
            .putString(Constants.KEY_LIKES, json)
            .apply()
    }

    fun getLikes(): MutableList<Like> {
        val json = prefs.getString(Constants.KEY_LIKES, null)

        return if (json != null) {
            val type = object : TypeToken<MutableList<Like>>() {}.type
            gson.fromJson(json, type)
        } else {
            mutableListOf()
        }
    }

    fun addLike(like: Like) {
        val likes = getLikes()

        val alreadyExists = likes.any {
            it.usuarioId == like.usuarioId && it.productoId == like.productoId
        }

        if (!alreadyExists) {
            likes.add(like)
            saveLikes(likes)
        }
    }

    fun removeLike(userId: Int, productId: Int) {
        val likes = getLikes()

        val updatedLikes = likes.filterNot {
            it.usuarioId == userId && it.productoId == productId
        }

        saveLikes(updatedLikes)
    }

    fun isProductLiked(userId: Int, productId: Int): Boolean {
        return getLikes().any {
            it.usuarioId == userId && it.productoId == productId
        }
    }

    fun clearAllData() {
        prefs.edit().clear().apply()
    }
}