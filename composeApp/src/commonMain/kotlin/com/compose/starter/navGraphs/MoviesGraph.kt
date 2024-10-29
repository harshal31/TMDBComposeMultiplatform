package com.compose.starter.navGraphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.compose.starter.di.ShareMediaData
import com.compose.starter.features.castAndCrewScreen.CastAndCrewListScreen
import com.compose.starter.features.movieDetailScreen.MovieDetailScreen
import com.compose.starter.features.movieDetailScreen.MovieDetailScreenModel
import com.compose.starter.features.moviesScreen.MoviesScreen
import com.compose.starter.features.moviesScreen.MoviesScreenViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.moviesGraph(navController: NavController) {
    navigation<Movie.Graph>(startDestination = Movie.Movies) {
        composable<Movie.Movies> {
            val viewModel = koinViewModel<MoviesScreenViewModel>()
            MoviesScreen(
                viewModel = viewModel,
                goToMoreScreen = {},
                onItemClick = { movieId ->
                    navController.navigate(Movie.Detail(movieId))
                }
            )
        }

        composable<Movie.Detail> {
            val viewModel = koinViewModel<MovieDetailScreenModel>()
            val shareData = it.toRoute<Movie.Detail>()
            MovieDetailScreen(
                id = shareData.movieId,
                viewModel = viewModel,
                navigate = { screen ->
                    screen.navigateToSpecificGraph(navController)
                }
            ) {
                navController.navigateUp()
            }
        }

        composable<Movie.CastAndCrewList> {
            val shareMediaData = koinInject<ShareMediaData>()
            CastAndCrewListScreen(
                shareMediaData = shareMediaData,
                navigateToDetail = { screen ->
                    screen.navigateToSpecificGraph(navController)
                }
            ) {
                navController.navigateUp()
            }
        }
    }
}


@Serializable
sealed interface Movie {
    @Serializable
    data object Graph : Movie

    @Serializable
    data object Movies : Movie

    @Serializable
    data class Detail(val movieId: String) : Movie

    @Serializable
    data object CastAndCrewList : Movie

    @Serializable
    data class CastDetail(val personId: Int) : Movie

    fun navigateToSpecificGraph(navController: NavController) {
        when (this) {
            is CastDetail -> navController.navigate(Person.Detail(personId))
            else -> navController.navigate(this)
        }
    }
}
