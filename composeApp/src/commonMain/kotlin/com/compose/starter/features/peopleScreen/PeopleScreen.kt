package com.compose.starter.features.peopleScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import composestarter.composeapp.generated.resources.weekly_trends

@Composable
fun PeopleScreen(
    padding: PaddingValues,
    viewModel: PeopleScreenViewModel,
    onEvent: (HomeScreenEvent) -> Unit,
    goToMoreScreen: (String) -> Unit,
    onItemClick: (String) -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        onEvent(HomeScreenEvent.ShowAppBar)
        viewModel.fetchPeopleList()
    }

    CleanContent(
        padding,
        uiState.apiState
    ) { paddingValues ->
        LazyColumn(
            Modifier
                .padding(paddingValues)
                .padding(MaterialTheme.spacing.default),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
        ) {
            listItemVisibility(
                key = Res.string.daily_trends.key,
                value = uiState.dailyTrendedPeople
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
                value = uiState.weeklyTrendingPeople
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
                value = uiState.popularPeople
            ) {
                TmdbHeaderTitleWithLazyRow(
                    headerTitle = Res.string.fan_favorites,
                    values = it,
                    goToMoreScreen = goToMoreScreen,
                    onItemClick = onItemClick
                )
            }
        }
    }
}