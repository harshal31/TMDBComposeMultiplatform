package com.compose.starter.appInitializations

import com.compose.starter.di.getKoinValue
import com.compose.starter.features.settingsScreen.ThemeMode
import com.compose.starter.localData.LocalStore
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object AppInitialLoad {
    fun loadData(callback: (AppInitialData) -> Unit) = runBlocking {
        launch {
            val localStore = getKoinValue<LocalStore>()
            val session = localStore.getString(LocalStore.SESSION_ID)
            val theme = localStore.getString(LocalStore.THEME)
            Napier.d("session in app initialized $session")

            callback(
                AppInitialData(
                    session = session,
                    isDarkTheme = theme == ThemeMode.Dark().value,
                    themeValue = theme
                )
            )
        }
    }
}

data class AppInitialData(
    val session: String,
    var isDarkTheme: Boolean?,
    var themeValue: String,
) {

    init {
        isDarkTheme = themeMode()
    }

    private fun themeMode(): Boolean? {
        return when (themeValue) {
            ThemeMode.Dark().value -> true
            ThemeMode.Light().value -> false
            else -> null
        }
    }

    fun isDarkThemeValue(value: Boolean): String {
        return if (value) {
            ThemeMode.Dark().value
        } else {
            ThemeMode.Light().value
        }
    }
}