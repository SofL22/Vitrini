package com.vitrini.app.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.sessionDataStore: DataStore<Preferences> by preferencesDataStore(name = "vitrini_preferences")
class SessionPreferencesDataStore (private val context: Context) {

    private object Keys {
        val activeUser = stringPreferencesKey("active_user")
        val activeUserId = stringPreferencesKey("active_user_id")
        val authToken = stringPreferencesKey("auth_token")
        val likes = stringPreferencesKey("likes")
    }

    private val safePreferencesFlow: Flow<Preferences> = context.sessionDataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }

    suspend fun saveActiveUser(userJson: String) {
        context.sessionDataStore.edit { it[Keys.activeUser] = userJson }
    }

    suspend fun getActiveUser(): String? = safePreferencesFlow.map { it[Keys.activeUser] }.first()

    suspend fun saveActiveUserId(userId: String) {
        context.sessionDataStore.edit { it[Keys.activeUserId] = userId }
    }

    suspend fun getActiveUserId(): String? = safePreferencesFlow.map { it[Keys.activeUserId] }.first()

    suspend fun saveAuthToken(token: String) {
        context.sessionDataStore.edit { it[Keys.authToken] = token }
    }

    suspend fun getAuthToken(): String? = safePreferencesFlow.map { it[Keys.authToken] }.first()

    suspend fun saveLikes(likesJson: String) {
        context.sessionDataStore.edit { it[Keys.likes] = likesJson }
    }

    suspend fun getLikes(): String? = safePreferencesFlow.map { it[Keys.likes] }.first()

    suspend fun clearSession() {
        context.sessionDataStore.edit {
            it.remove(Keys.activeUser)
            it.remove(Keys.activeUserId)
            it.remove(Keys.authToken)
        }
    }

    suspend fun clearAll() {
        context.sessionDataStore.edit { it.clear() }
    }
}