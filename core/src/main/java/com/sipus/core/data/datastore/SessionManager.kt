package com.sipus.core.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "sipus_session")

@Singleton
class SessionManager @Inject constructor(private val context: Context) {

    companion object {
        private val KEY_TOKEN = stringPreferencesKey("token")
        private val KEY_USER_ID = intPreferencesKey("user_id")
        private val KEY_USERNAME = stringPreferencesKey("username")
        private val KEY_NAMA = stringPreferencesKey("nama")
        private val KEY_ROLE = stringPreferencesKey("role")
        private val KEY_FOTO = stringPreferencesKey("foto")
        private val KEY_EMAIL = stringPreferencesKey("email")
    }

    val token: Flow<String?> = context.dataStore.data.map { it[KEY_TOKEN] }
    val role: Flow<String?> = context.dataStore.data.map { it[KEY_ROLE] }
    val userId: Flow<Int?> = context.dataStore.data.map { it[KEY_USER_ID] }
    val userName: Flow<String?> = context.dataStore.data.map { it[KEY_NAMA] }
    val username: Flow<String?> = context.dataStore.data.map { it[KEY_USERNAME] }

    suspend fun getToken(): String? {
        var result: String? = null
        context.dataStore.data.collect { result = it[KEY_TOKEN] }
        return result
    }

    suspend fun saveSession(token: String, id: Int, username: String, nama: String, role: String, foto: String? = null, email: String? = null) {
        context.dataStore.edit {
            it[KEY_TOKEN] = token
            it[KEY_USER_ID] = id
            it[KEY_USERNAME] = username
            it[KEY_NAMA] = nama
            it[KEY_ROLE] = role
            foto?.let { f -> it[KEY_FOTO] = f }
            email?.let { e -> it[KEY_EMAIL] = e }
        }
    }

    suspend fun updateToken(token: String) {
        context.dataStore.edit { it[KEY_TOKEN] = token }
    }

    suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
    }
}
