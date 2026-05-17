package com.vitrini.app.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vitrini.app.data.local.entity.LikeEntity
import com.vitrini.app.data.local.preferences.SessionData
import com.vitrini.app.data.local.preferences.SessionPreferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class SessionManager(context: Context) {
    private val gson = Gson()

    private val preferencesDataStore = SessionPreferencesDataStore(context)

    fun saveSession(authToken: String, userId: String, accountType: String) {
        runBlocking { preferencesDataStore.saveSession(authToken, userId, accountType) }
    }

    fun clearSession() {
        runBlocking { preferencesDataStore.clearSession() }
    }

    fun observeSession(): Flow<SessionData> = preferencesDataStore.observeSession()

    fun getAuthToken(): String? = runBlocking { preferencesDataStore.getAuthToken() }

    fun getActiveUserId(): String? = runBlocking { preferencesDataStore.observeSession().first().userId }

    fun isUserLoggedIn(): Boolean = runBlocking { preferencesDataStore.observeSession().first().isLoggedIn }

    fun logout() = clearSession()

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

    fun clearAllData() {
        runBlocking { preferencesDataStore.clearAll() }
    }
}