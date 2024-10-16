package com.compose.starter.features.moviesScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compose.starter.commonUi.CleanContent
import com.compose.starter.commonUi.TmdbHeaderTitleWithLazyRow
import com.compose.starter.commonUi.listItemVisibility
import com.compose.starter.features.homeScreen.HomeScreenEvent
import com.compose.starter.spacingsAndBorders.spacing
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.daily_trends
import composestarter.composeapp.generated.resources.fan_favorites
import composestarter.composeapp.generated.resources.free_flicks
import composestarter.composeapp.generated.resources.future_flicks
import composestarter.composeapp.generated.resources.theatre_thrills
import composestarter.composeapp.generated.resources.weekly_trends
import io.github.aakira.napier.Napier

@Composable
fun MoviesScreen(
    padding: PaddingValues,
    viewModel: MoviesScreenViewModel,
    onEvent: (HomeScreenEvent) -> Unit,
    goToMoreScreen: (String) -> Unit,
    onItemClick: (String) -> Unit,
) {
    LaunchedEffect(Unit) {
        onEvent(HomeScreenEvent.ShowAppBar)
        viewModel.fetchMoviesScreenData()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SideEffect {
        Napier.d("Recomposing the compose")
    }

    CleanContent(padding, uiState.errorCode) { paddingValues ->
        LazyColumn(
            Modifier
                .padding(paddingValues)
                .padding(MaterialTheme.spacing.default),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
        ) {
            listItemVisibility(
                key = Res.string.theatre_thrills.key,
                value = uiState.nowInTheatres
            ) {
                TmdbHeaderTitleWithLazyRow(
                    headerTitle = Res.string.theatre_thrills,
                    values = it,
                    goToMoreScreen = goToMoreScreen,
                    onItemClick = onItemClick
                )
            }
            listItemVisibility(
                key = Res.string.daily_trends.key,
                value = uiState.dailyTrendedMovies
            ) {
                TmdbHeaderTitleWithLazyRow(
                    headerTitle = Res.string.daily_trends,
                    values = it,
                    goToMoreScreen = goToMoreScreen,
                    onItemClick = onItemClick
                )
            }
            listItemVisibility(
                key = Res.string.weekly_trends.key,
                value = uiState.weeklyTrendingMovies
            ) {
                TmdbHeaderTitleWithLazyRow(
                    headerTitle = Res.string.weekly_trends,
                    values = it,
                    goToMoreScreen = goToMoreScreen,
                    onItemClick = onItemClick
                )
            }
            listItemVisibility(
                key = Res.string.fan_favorites.key,
                value = uiState.popularMovies
            ) {
                TmdbHeaderTitleWithLazyRow(
                    headerTitle = Res.string.fan_favorites,
                    values = it,
                    goToMoreScreen = goToMoreScreen,
                    onItemClick = onItemClick
                )
            }
            listItemVisibility(
                key = Res.string.future_flicks.key,
                value = uiState.upcomingMovies
            ) {
                TmdbHeaderTitleWithLazyRow(
                    headerTitle = Res.string.future_flicks,
                    values = it,
                    goToMoreScreen = goToMoreScreen,
                    onItemClick = onItemClick
                )
            }
            listItemVisibility(
                key = Res.string.free_flicks.key,
                value = uiState.freeToWatchMovies
            ) {
                TmdbHeaderTitleWithLazyRow(
                    headerTitle = Res.string.free_flicks,
                    values = it,
                    goToMoreScreen = goToMoreScreen,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

