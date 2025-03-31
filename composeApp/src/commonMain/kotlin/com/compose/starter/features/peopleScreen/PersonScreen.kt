package com.compose.starter.features.peopleScreen

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
import composestarter.composeapp.generated.resources.weekly_trends

@Composable
fun PersonScreen(
    viewModel: PersonScreenViewModel,
    goToMoreScreen: (String) -> Unit,
    onItemClick: (String) -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchPeopleList()
    }

    Scaffold {
        CleanContent(
            PaddingValues(),
            uiState.apiState
        ) { paddingValues ->
            LazyColumn(
                Modifier
                    .padding(paddingValues)
                    .padding(MaterialTheme.spacing.default),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
            ) {

                item {
                    HeaderTitle(
                        headerTitle = Res.string.daily_trends
                    ) {

                    }
                }

                item {
                    HeaderListValue(
                        values = uiState.dailyTrendedPeople,
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
                        values = uiState.weeklyTrendingPeople,
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
                        values = uiState.popularPeople,
                        onItemClick = onItemClick
                    )
                }
            }
        }
    }
}