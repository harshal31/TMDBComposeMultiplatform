package com.compose.starter.navGraphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.compose.starter.features.homeScreen.HomeScreenEvent
import com.compose.starter.features.tvSeriesScreen.TvSeriesScreen
import com.compose.starter.features.tvSeriesScreen.TvSeriesScreenViewModel
import com.compose.starter.routes.Screen.Graphs.TvSeriesGraph
import com.compose.starter.routes.Screen.TvSeries
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.tvSeriesGraph(
    paddingValues: PaddingValues,
    navController: NavController,
    onEvent: (HomeScreenEvent) -> Unit,
) {
    navigation<TvSeriesGraph>(startDestination = TvSeries) {
        composable<TvSeries> {
            val viewModel = koinViewModel<TvSeriesScreenViewModel>()
            TvSeriesScreen(
                padding = paddingValues,
                viewModel = viewModel,
                onEvent = onEvent,
                goToMoreScreen = {},
                onItemClick = {}
            )
        }
    }
}