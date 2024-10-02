package com.compose.starter.localData

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class LocalStore(private val store: DataStore<Preferences>) {

    suspend fun putString(key: String, value: String?) {
        store.edit {
            val prefKey = stringPreferencesKey(key)
            it[prefKey] = value ?: ""
        }
    }

    suspend fun putBoolean(key: String, value: Boolean?) {
        store.edit {
            val prefKey = booleanPreferencesKey(key)
            it[prefKey] = value ?: false
        }
    }


    fun getString(key: String): String {
        val prefKey = stringPreferencesKey(key)
        return runBlocking { store.data.first()[prefKey] ?: "" }
    }

    fun getAsyncString(key: String): Flow<String?> {
        return store.data.map {
            val prefKey = stringPreferencesKey(key)
            it[prefKey]
        }
    }


    fun getBool(key: String): Boolean {
        val prefKey = booleanPreferencesKey(key)
        return runBlocking { store.data.first()[prefKey] ?: false }
    }

    fun getAsyncBool(key: String): Flow<Boolean?> {
        return store.data.map {
            val prefKey = booleanPreferencesKey(key)
            it[prefKey]
        }
    }


    companion object {
        const val TOKEN_ID = "token_id"
        const val SESSION_ID = "session_id"
        const val ACCOUNT_ID = "account_id"
        const val THEME = "theme"
    }
}