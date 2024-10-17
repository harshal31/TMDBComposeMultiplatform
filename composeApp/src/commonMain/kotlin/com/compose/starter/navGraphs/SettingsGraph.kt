package com.compose.starter.navGraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.compose.starter.features.settingsScreen.SettingsScreen
import com.compose.starter.features.settingsScreen.SettingsScreenViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel


fun NavGraphBuilder.settingsGraph() {
    navigation<Setting.Graph>(startDestination = Setting.Settings) {
        composable<Setting.Settings> {
            val viewModel = koinViewModel<SettingsScreenViewModel>()
            SettingsScreen(viewModel = viewModel)
        }
    }
}

@Serializable
sealed interface Setting {
    @Serializable
    data object Graph: Setting
    @Serializable
    data object Settings: Setting

}
