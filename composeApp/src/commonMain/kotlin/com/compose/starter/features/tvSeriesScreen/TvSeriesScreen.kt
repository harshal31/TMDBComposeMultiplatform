package com.compose.starter.features.tvSeriesScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compose.starter.commonUi.CleanContent
import com.compose.starter.commonUi.HeaderListValue
import com.compose.starter.commonUi.HeaderTitle
import com.compose.starter.spacingsAndBorders.spacing
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.daily_trends
import composestarter.composeapp.generated.resources.fan_favorites
import composestarter.composeapp.generated.resources.free_flicks
import composestarter.composeapp.generated.resources.future_shows
import composestarter.composeapp.generated.resources.on_air
import composestarter.composeapp.generated.resources.weekly_trends

@Composable
fun TvSeriesScreen(
    viewModel: TvSeriesScreenViewModel,
    goToMoreScreen: (String) -> Unit,
    onItemClick: (String) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.fetchTvSeriesScreenData()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold {
        CleanContent(PaddingValues(), uiState.errorCode) { paddingValues ->
            LazyColumn(
                Modifier
                    .padding(paddingValues)
                    .padding(MaterialTheme.spacing.default),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
            ) {

                item {
                    HeaderTitle(
                        headerTitle = Res.string.on_air
                    ) {

                    }
                }

                item {
                    HeaderListValue(
                        values = uiState.airingToday,
                        onItemClick = onItemClick
                    )
                }


                item {
                    HeaderTitle(
                        headerTitle = Res.string.daily_trends
                    ) {

                    }
                }

                item {
                    HeaderListValue(
                        values = uiState.dailyTrendedTvSeries,
                        onItemClick = onItemClick
                    )
                }


                item {
                    HeaderTitle(
                        headerTitle = Res.string.weekly_trends
                    ) {

                    }
                }

                item {
                    HeaderListValue(
                        values = uiState.weeklyTrendingTvSeries,
                        onItemClick = onItemClick
                    )
                }


                item {
                    HeaderTitle(
                        headerTitle = Res.string.fan_favorites
                    ) {

                    }
                }

                item {
                    HeaderListValue(
                        values = uiState.popularTvSeries,
                        onItemClick = onItemClick
                    )
                }


                item {
                    HeaderTitle(
                        headerTitle = Res.string.future_shows
                    ) {

                    }
                }

                item {
                    HeaderListValue(
                        values = uiState.upcomingTvSeries,
                        onItemClick = onItemClick
                    )
                }


                item {
                    HeaderTitle(
                        headerTitle = Res.string.free_flicks
                    ) {

                    }
                }

                item {
                    HeaderListValue(
                        values = uiState.freeToWatchTvSeries,
                        onItemClick = onItemClick
                    )
                }
            }
        }
    }
}