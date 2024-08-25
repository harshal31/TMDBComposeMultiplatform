package com.compose.starter.localData

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalStore(private val store: DataStore<Preferences>) {

    suspend fun putString(key: String, value: String) {
        store.edit {
            val prefKey = stringPreferencesKey(key)
            it[prefKey] = value
        }
    }

    fun getString(key: String): Flow<String?> {
        return store.data.map {
            val prefKey = stringPreferencesKey(key)
            it[prefKey]
        }
    }


    companion object {
        const val TOKEN_ID = "token_id"
        const val SESSION_ID = "session_id"
    }
}