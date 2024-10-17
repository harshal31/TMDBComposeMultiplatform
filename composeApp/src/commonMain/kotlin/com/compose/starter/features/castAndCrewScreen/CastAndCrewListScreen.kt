package com.compose.starter.features.castAndCrewScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
    onBack: () -> Unit,
) {
    val typography = MaterialTheme.typography
    val uiState by shareMediaData.movieState.collectAsStateWithLifecycle()
    val gridCells = 3
    val totalCols = remember { uiState.casts.chunked(gridCells).size }
    val gridItemSize = MaterialTheme.sizing.twoFifty
    val spaceBetween = MaterialTheme.spacing.small
    val verticalGridHeight =
        remember {
            (gridItemSize.times(totalCols)) + (spaceBetween.times(totalCols))
        }

    val castTitle = pluralResource(
        res = Res.string.cast to Res.plurals.cast,
        quantity = uiState.casts.size,
        formatArgs = arrayOf(uiState.casts.size)
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
                navigationIcon = {
                    CircleIcon(
                        modifier = Modifier.size(MaterialTheme.sizing.extraLarge),
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
                        items(uiState.casts, key = { it.id ?: "" }) {
                            CastItem(
                                Modifier
                                    .fillMaxWidth()
                                    .height(gridItemSize),
                                it
                            )
                        }
                    }
                )
            }

            item(key = "tmdb_divider") {
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

            uiState.crews.forEach { (title, values) ->
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
                            items(values, key = { it.hashCode() }) {
                                CrewItem(
                                    Modifier
                                        .fillMaxWidth()
                                        .height(gridItemSize),
                                    it
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
private fun CastItem(modifier: Modifier, it: MappedCast) {
    OutlinedCard(modifier = modifier) {
        Column(modifier = modifier) {
            CoilFullSizeImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.sizing.oneForty)
                    .clip(MaterialTheme.rectBorder.bottomLevelMediumBorder),
                url = it.profilePath,
                contentScale = ContentScale.Crop,
                contentDescription = ContentDescription.personImage(it.name),
                errorPlaceholder = painterResource(Res.drawable.people_filled)
            )
            Spacer(Modifier.height(MaterialTheme.spacing.small))
            Text(
                it.name,
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraSmall),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))
            Text(
                it.character,
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
    it: MappedCrew,
) {
    OutlinedCard(modifier = modifier) {
        Column(modifier = modifier) {
            CoilFullSizeImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.sizing.oneForty)
                    .clip(MaterialTheme.rectBorder.bottomLevelMediumBorder),
                url = it.profilePath,
                contentScale = ContentScale.Crop,
                contentDescription = ContentDescription.personImage(it.name),
                errorPlaceholder = painterResource(Res.drawable.people_filled)
            )
            Spacer(Modifier.height(MaterialTheme.spacing.small))
            Text(
                it.name,
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraSmall),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))
            Text(
                it.job,
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