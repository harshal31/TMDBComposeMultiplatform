package com.compose.starter.features.settingsScreen

import androidx.compose.runtime.Stable
import com.compose.starter.networking.ApiState
import com.compose.starter.networking.model.AccountDetail
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.dark_mode
import composestarter.composeapp.generated.resources.dark_theme
import composestarter.composeapp.generated.resources.light_mode
import composestarter.composeapp.generated.resources.light_theme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

@Stable
data class SettingScreenUiState(
    val theme: ThemeMode = ThemeMode.None,
    val apiState: ApiState = ApiState.CIRCULAR_LOADING,
    val accountDetail: AccountDetail? = null,
)

sealed interface ThemeMode {
    data class Dark(
        val res: DrawableResource = Res.drawable.dark_mode,
        val stringRes: StringResource = Res.string.dark_theme,
        val value: String = "dark",
        val boolValue: Boolean = true,
    ) : ThemeMode

    data class Light(
        val res: DrawableResource = Res.drawable.light_mode,
        val stringRes: StringResource = Res.string.light_theme,
        val value: String = "light",
        val boolValue: Boolean = false,
    ) : ThemeMode

    data object None : ThemeMode


    companion object {

        fun determineThemeMode(value: String?): ThemeMode {
            return when (value) {
                Light().value -> Light()
                Dark().value -> Dark()
                else -> None
            }
        }

        fun determineThemeMode(value: Boolean): ThemeMode {
            return if (value) {
                Dark()
            } else {
                Light()
            }
        }

        fun determineThemeValue(theme: ThemeMode): String {
            return when (theme) {
                is Dark -> theme.value
                is Light -> theme.value
                None -> ""
            }
        }

        fun isDarkTheme(value: String?): Boolean? {
            return when (value?.lowercase()) {
                Light().value -> false
                Dark().value -> true
                else -> null
            }
        }
    }
}