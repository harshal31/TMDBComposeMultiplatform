package com.compose.starter.features.personDetailScreen.personDetailUiComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.compose.starter.constants.ContentDescription
import com.compose.starter.networking.model.MappedMovieCredit
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing
import com.compose.starter.utilities.ImmutableMap
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.as_character
import composestarter.composeapp.generated.resources.movies
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesBottomSheet(
    sheetState: SheetState,
    isBottomSheetVisible: Boolean,
    movieCredits: ImmutableMap<String, List<MappedMovieCredit>>,
    navigateToMovieRole: (String) -> Unit,
    onDismiss: () -> Unit
) {
    if (isBottomSheetVisible) {
        ModalBottomSheet(
            modifier = Modifier.statusBarsPadding(),
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = MaterialTheme.spacing.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        stringResource(Res.string.movies),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = ContentDescription.CLOSE,
                            modifier = Modifier.size(MaterialTheme.sizing.large)
                        )
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                item {
                    movieCredits.map.keys.forEach { key ->
                        key(key) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                            ) {
                                Text(
                                    key,
                                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                MoviesTitleWithCharacter(
                                    movies = movieCredits.map[key] ?: emptyList(),
                                    navigateToMovieRole
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MoviesTitleWithCharacter(
    movies: List<MappedMovieCredit>,
    navigateToMovieRole: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
    ) {
        movies.forEach {
            key(it.id) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navigateToMovieRole(it.id.toString())
                        }
                        .padding(horizontal = MaterialTheme.spacing.default),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                ) {
                    Text(
                        it.year ?: "",
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Light
                    )
                    Column(
                        modifier = Modifier
                            .weight(5f)
                            .wrapContentHeight(),
                    ) {
                        Text(
                            it.movieName ?: "-",
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.tiny))

                        Text(
                            stringResource(Res.string.as_character, it.charOrJob ?: "-"),
                            fontWeight = FontWeight.Light
                        )
                    }
                }
            }
        }
    }
}