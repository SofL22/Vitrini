package com.vitrini.app.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vitrini.app.data.local.entity.LikeEntity
import com.vitrini.app.data.local.entity.UserEntity
import com.vitrini.app.data.local.preferences.SessionPreferencesDataStore
import kotlinx.coroutines.runBlocking

class SessionManager(context: Context) {
    private val gson = Gson()

    private val preferencesDataStore = SessionPreferencesDataStore(context)

    fun saveActiveUser(user: UserEntity) {
        val json = gson.toJson(user)
        runBlocking { preferencesDataStore.saveActiveUser(json) }
    }

    fun getActiveUser(): UserEntity? {
        val json = runBlocking { preferencesDataStore.getActiveUser() }
        return if (json != null) gson.fromJson(json, UserEntity::class.java) else null
    }

    fun isUserLoggedIn(): Boolean = getActiveUser() != null || getActiveUserId() != null

    fun saveActiveUserId(userId: String) {
        runBlocking { preferencesDataStore.saveActiveUserId(userId) }
    }

    fun getActiveUserId(): String? = runBlocking { preferencesDataStore.getActiveUserId() }

    fun saveAuthToken(token: String) {
        runBlocking { preferencesDataStore.saveAuthToken(token) }
    }

    fun getAuthToken(): String? = runBlocking { preferencesDataStore.getAuthToken() }

    fun logout() {
        runBlocking { preferencesDataStore.clearSession() }
    }

    fun saveLikes(likes: List<LikeEntity>) {
        val json = gson.toJson(likes)
        runBlocking { preferencesDataStore.saveLikes(json) }
    }

    fun getLikes(): MutableList<LikeEntity> {
        val json = runBlocking { preferencesDataStore.getLikes() }

        return if (json != null) {
            val type = object : TypeToken<MutableList<LikeEntity>>() {}.type
            gson.fromJson(json, type)
        } else {
            mutableListOf()
        }
    }

    fun addLike(like: LikeEntity) {
        val likes = getLikes()

        val alreadyExists = likes.any { it.userId == like.userId && it.productId == like.productId }

        if (!alreadyExists) {
            likes.add(like)
            saveLikes(likes)
        }
    }

    fun removeLike(userId: Int, productId: Int) {
        val updatedLikes = getLikes().filterNot { it.userId == userId && it.productId == productId }

        saveLikes(updatedLikes)
    }

    fun isProductLiked(userId: Int, productId: Int): Boolean =
        getLikes().any { it.userId == userId && it.productId == productId }

    fun clearAllData() {
        runBlocking { preferencesDataStore.clearAll() }
    }
}