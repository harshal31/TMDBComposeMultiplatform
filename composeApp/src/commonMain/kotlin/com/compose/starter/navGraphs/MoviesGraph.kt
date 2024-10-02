package com.compose.starter.navGraphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.compose.starter.features.homeScreen.HomeScreenEvent
import com.compose.starter.features.movieDetailScreen.MovieDetailScreen
import com.compose.starter.features.movieDetailScreen.MovieDetailScreenModel
import com.compose.starter.features.moviesScreen.MoviesScreen
import com.compose.starter.features.moviesScreen.MoviesScreenViewModel
import com.compose.starter.routes.Screen.Graphs.MoviesGraph
import com.compose.starter.routes.Screen.MovieDetailScreen
import com.compose.starter.routes.Screen.Movies
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.moviesGraph(
    paddingValues: PaddingValues,
    navController: NavController,
    onEvent: (HomeScreenEvent) -> Unit,
) {
    navigation<MoviesGraph>(startDestination = Movies) {
        composable<Movies> {
            val viewModel = koinViewModel<MoviesScreenViewModel>()
            MoviesScreen(
                viewModel = viewModel,
                padding = paddingValues,
                onEvent = onEvent,
                goToMoreScreen = {},
                onItemClick = {
                    navController.navigate(MovieDetailScreen(it))
                }
            )
        }

        composable<MovieDetailScreen> {
            val viewModel = koinViewModel<MovieDetailScreenModel>()
            val shareData = it.toRoute<MovieDetailScreen>()
            MovieDetailScreen(
                padding = paddingValues,
                id = shareData.id,
                onEvent = onEvent,
                viewModel = viewModel,
                onStatusBarColorChange = { color ->
                    onEvent(HomeScreenEvent.UpdateStatusColor(color))
                }
            ) {
                navController.navigateUp()
            }
        }
    }
}