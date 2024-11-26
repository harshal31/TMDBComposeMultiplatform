package com.compose.starter.features.castAndCrewScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compose.starter.commonUi.CircleIcon
import com.compose.starter.commonUi.CoilFullSizeImage
import com.compose.starter.commonUi.TmdbDivider
import com.compose.starter.constants.ContentDescription
import com.compose.starter.di.ShareMediaData
import com.compose.starter.navGraphs.Movie
import com.compose.starter.networking.model.MappedCast
import com.compose.starter.networking.model.MappedCrew
import com.compose.starter.spacingsAndBorders.rectBorder
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing
import com.compose.starter.utilities.pluralResource
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.arrow_back
import composestarter.composeapp.generated.resources.cast
import composestarter.composeapp.generated.resources.crew
import composestarter.composeapp.generated.resources.people_filled
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CastAndCrewListScreen(
    shareMediaData: ShareMediaData,
    navigateToDetail: (Movie) -> Unit,
    onBack: () -> Unit,
) {
    val typography = MaterialTheme.typography
    val uiState by shareMediaData.movieState.collectAsStateWithLifecycle()
    val gridCells = 3
    val totalCols = remember { uiState.casts.items.chunked(gridCells).size }
    val gridItemSize = MaterialTheme.sizing.twoFifty
    val spaceBetween = MaterialTheme.spacing.small
    val verticalGridHeight =
        remember {
            (gridItemSize.times(totalCols)) + (spaceBetween.times(totalCols))
        }

    val castTitle = pluralResource(
        res = Res.string.cast to Res.plurals.cast,
        quantity = uiState.casts.items.size,
        formatArgs = arrayOf(uiState.casts.items.size)
    )

    val crewTitle = pluralResource(
        res = Res.string.crew to Res.plurals.crew,
        quantity = uiState.crewSize,
        formatArgs = arrayOf(uiState.crewSize)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(start = MaterialTheme.spacing.default),
                navigationIcon = {
                    CircleIcon(
                        icon = Res.drawable.arrow_back,
                        onIconClick = onBack
                    )
                },
                title = {
                    Text(
                        uiState.mediaName,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
            )
        }
    ) { padValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues)
                .padding(horizontal = MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            item(key = castTitle) {
                Text(
                    castTitle,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            item(key = uiState.casts.hashCode()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(gridCells),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(verticalGridHeight),
                    horizontalArrangement = Arrangement.spacedBy(spaceBetween),
                    verticalArrangement = Arrangement.spacedBy(spaceBetween),
                    content = {
                        items(
                            uiState.casts.items,
                            key = { it.id ?: "" }
                        ) {
                            CastItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(gridItemSize),
                                mappedCast = it,
                                navigateToDetail = navigateToDetail
                            )
                        }
                    }
                )
            }

            item {
                TmdbDivider(
                    modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium),
                    isVertical = false,
                )
            }

            item(key = crewTitle) {
                Text(
                    crewTitle,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            uiState.crews.map.forEach { (title, values) ->
                val crewChunked = values.chunked(gridCells).size
                val crewVerticalGridHeight =
                    (gridItemSize.times(crewChunked)) + (spaceBetween.times(crewChunked))

                item(key = title) {
                    Text(
                        title,
                        style = typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                item(key = values.hashCode()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(gridCells),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(crewVerticalGridHeight),
                        horizontalArrangement = Arrangement.spacedBy(spaceBetween),
                        verticalArrangement = Arrangement.spacedBy(spaceBetween),
                        content = {
                            items(
                                values,
                                key = { it.hashCode() }
                            ) {
                                CrewItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(gridItemSize),
                                    mappedCrew = it,
                                    navigateToDetail = navigateToDetail
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CastItem(
    modifier: Modifier,
    mappedCast: MappedCast,
    navigateToDetail: (Movie) -> Unit
) {
    OutlinedCard(
        modifier = modifier,
        onClick = {
            navigateToDetail(Movie.CastDetail(mappedCast.id ?: -1))
        }
    ) {
        Column(modifier = modifier) {
            CoilFullSizeImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.sizing.oneForty)
                    .clip(MaterialTheme.rectBorder.bottomLevelMediumBorder),
                url = mappedCast.profilePath,
                contentScale = ContentScale.Crop,
                contentDescription = ContentDescription.personImage(mappedCast.name),
                errorPlaceholder = painterResource(Res.drawable.people_filled)
            )
            Spacer(Modifier.height(MaterialTheme.spacing.small))
            Text(
                mappedCast.name,
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraSmall),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))
            Text(
                mappedCast.character,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraSmall),
                color = MaterialTheme.colorScheme.primary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))
        }
    }
}


@Composable
private fun CrewItem(
    modifier: Modifier,
    mappedCrew: MappedCrew,
    navigateToDetail: (Movie) -> Unit,
) {
    OutlinedCard(
        modifier = modifier,
        onClick = {
            navigateToDetail(Movie.CastDetail(mappedCrew.id ?: -1))
        }
    ) {
        Column(modifier = modifier) {
            CoilFullSizeImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.sizing.oneForty)
                    .clip(MaterialTheme.rectBorder.bottomLevelMediumBorder),
                url = mappedCrew.profilePath,
                contentScale = ContentScale.Crop,
                contentDescription = ContentDescription.personImage(mappedCrew.name),
                errorPlaceholder = painterResource(Res.drawable.people_filled)
            )
            Spacer(Modifier.height(MaterialTheme.spacing.small))
            Text(
                mappedCrew.name,
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraSmall),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))
            Text(
                mappedCrew.job,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraSmall),
                color = MaterialTheme.colorScheme.primary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))
        }
    }
}