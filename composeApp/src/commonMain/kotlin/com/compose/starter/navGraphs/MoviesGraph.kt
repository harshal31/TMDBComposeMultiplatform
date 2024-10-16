package com.compose.starter.navGraphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.compose.starter.features.castAndCrewScreen.CastAndCrewScreen
import com.compose.starter.features.homeScreen.HomeScreenEvent
import com.compose.starter.features.movieDetailScreen.MovieDetailScreen
import com.compose.starter.features.movieDetailScreen.MovieDetailScreenModel
import com.compose.starter.features.moviesScreen.MoviesScreen
import com.compose.starter.features.moviesScreen.MoviesScreenViewModel
import com.compose.starter.features.peopleDetailScreen.PeopleDetailScreen
import com.compose.starter.routes.Screen
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.moviesGraph(
    paddingValues: PaddingValues,
    navController: NavController,
    onEvent: (HomeScreenEvent) -> Unit,
) {
    navigation<Screen.Graphs.MoviesGraph>(startDestination = Screen.Movies) {
        composable<Screen.Movies> {
            val viewModel = koinViewModel<MoviesScreenViewModel>()
            MoviesScreen(
                viewModel = viewModel,
                padding = paddingValues,
                onEvent = onEvent,
                goToMoreScreen = {},
                onItemClick = { movieId ->
                    navController.navigate(MovieNavigation.MovieDetailScreen(movieId))
                }
            )
        }

        composable<MovieNavigation.MovieDetailScreen> {
            val viewModel = koinViewModel<MovieDetailScreenModel>()
            val shareData = it.toRoute<MovieNavigation.MovieDetailScreen>()
            MovieDetailScreen(
                padding = paddingValues,
                id = shareData.movieId,
                onEvent = onEvent,
                viewModel = viewModel,
                navigateToDetail = { screen ->
                    navController.navigate(screen)
                },
                onStatusBarColorChange = { color ->
                    onEvent(HomeScreenEvent.UpdateStatusColor(color))
                }
            ) {
                navController.navigateUp()
            }
        }

        composable<MovieNavigation.CastAndCrewListScreen> {
            CastAndCrewScreen(
                padding = paddingValues,
            )
        }

        composable<MovieNavigation.PeopleScreen> {
            val shareData = it.toRoute<MovieNavigation.PeopleScreen>()
            PeopleDetailScreen(
                padding = paddingValues,
                castId = shareData.castId
            )
        }
    }
}


sealed interface MovieNavigation {
    @Serializable
    data class MovieDetailScreen(val movieId: String) : MovieNavigation

    @Serializable
    data object CastAndCrewListScreen : MovieNavigation

    @Serializable
    data class PeopleScreen(val castId: Int) : MovieNavigation
}
