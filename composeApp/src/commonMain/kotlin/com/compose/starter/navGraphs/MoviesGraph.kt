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
import com.compose.starter.features.peopleDetailScreen.PeopleDetailScreen
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
                    navController.navigate(Movie.DetailScreen(movieId))
                }
            )
        }

        composable<Movie.DetailScreen> {
            val viewModel = koinViewModel<MovieDetailScreenModel>()
            val shareData = it.toRoute<Movie.DetailScreen>()
            MovieDetailScreen(
                id = shareData.movieId,
                viewModel = viewModel,
                navigateToDetail = { screen ->
                    navController.navigate(screen)
                }
            ) {
                navController.navigateUp()
            }
        }

        composable<Movie.CastAndCrewListScreen> {
            val shareMediaData = koinInject<ShareMediaData>()
            CastAndCrewListScreen(
                shareMediaData = shareMediaData,
            ) {
                navController.navigateUp()
            }
        }

        composable<Movie.PeopleScreen> {
            val shareData = it.toRoute<Movie.PeopleScreen>()
            PeopleDetailScreen(
                castId = shareData.castId
            )
        }
    }
}


@Serializable
sealed interface Movie {

    @Serializable
    data object Graph: Movie

    @Serializable
    data object Movies: Movie

    @Serializable
    data class DetailScreen(val movieId: String) : Movie

    @Serializable
    data object CastAndCrewListScreen : Movie

    @Serializable
    data class PeopleScreen(val castId: Int) : Movie
}
