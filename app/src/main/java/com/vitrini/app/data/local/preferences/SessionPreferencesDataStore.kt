package com.vitrini.app.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
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
class SessionPreferencesDataStore(private val context: Context) {

    private object Keys {
        val authToken = stringPreferencesKey("auth_token")
        val userId = stringPreferencesKey("user_id")
        val accountType = stringPreferencesKey("account_type")
        val isLoggedIn = booleanPreferencesKey("is_logged_in")
        val likes = stringPreferencesKey("likes")
    }

    private val safePreferencesFlow: Flow<Preferences> = context.sessionDataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }

    suspend fun saveSession(authToken: String, userId: String, accountType: String) {
        context.sessionDataStore.edit {
            it[Keys.authToken] = authToken
            it[Keys.userId] = userId
            it[Keys.accountType] = accountType
            it[Keys.isLoggedIn] = true
        }
    }

        fun observeSession(): Flow<SessionData> = safePreferencesFlow.map {
            SessionData(
                authToken = it[Keys.authToken],
                userId = it[Keys.userId],
                accountType = it[Keys.accountType],
                isLoggedIn = it[Keys.isLoggedIn] ?: false
            )
        }

        suspend fun getAuthToken(): String? = safePreferencesFlow.map { it[Keys.authToken] }.first()

        suspend fun clearSession() {
            context.sessionDataStore.edit {
                it.remove(Keys.authToken)
                it.remove(Keys.userId)
                it.remove(Keys.accountType)
                it[Keys.isLoggedIn] = false
            }
    }


    suspend fun saveLikes(likesJson: String) {
        context.sessionDataStore.edit { it[Keys.likes] = likesJson }
    }

    suspend fun getLikes(): String? = safePreferencesFlow.map { it[Keys.likes] }.first()


    suspend fun clearAll() {
        context.sessionDataStore.edit { it.clear() }
    }
}

data class SessionData(
    val authToken: String?,
    val userId: String?,
    val accountType: String?,
    val isLoggedIn: Boolean
)