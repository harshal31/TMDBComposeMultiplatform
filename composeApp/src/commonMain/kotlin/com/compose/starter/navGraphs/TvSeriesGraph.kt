package com.compose.starter.navGraphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.compose.starter.features.tvSeriesScreen.TvSeriesScreen
import com.compose.starter.features.tvSeriesScreen.TvSeriesScreenViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.tvSeriesGraph(navController: NavController) {
    navigation<Tv.Graph>(startDestination = Tv.TvSeries) {
        composable<Tv.TvSeries> {
            val viewModel = koinViewModel<TvSeriesScreenViewModel>()
            TvSeriesScreen(
                viewModel = viewModel,
                goToMoreScreen = {},
                onItemClick = {}
            )
        }
    }
}


@Serializable
sealed interface Tv {
    @Serializable
    data object Graph: Tv
    @Serializable
    data object TvSeries: Tv
}
