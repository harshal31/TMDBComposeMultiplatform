package com.compose.starter.navGraphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.compose.starter.features.homeScreen.HomeScreenEvent
import com.compose.starter.features.settingsScreen.SettingsScreen
import com.compose.starter.features.settingsScreen.SettingsScreenViewModel
import com.compose.starter.routes.Screen.Graphs.SettingsGraph
import com.compose.starter.routes.Screen.Settings
import org.koin.compose.viewmodel.koinViewModel


fun NavGraphBuilder.settingsGraph(
    paddingValues: PaddingValues,
    onEvent: (HomeScreenEvent) -> Unit,
) {
    navigation<SettingsGraph>(startDestination = Settings) {
        composable<Settings> {
            val viewModel = koinViewModel<SettingsScreenViewModel>()
            SettingsScreen(
                padding = paddingValues,
                onEvent = onEvent,
                viewModel = viewModel
            )
        }
    }
}