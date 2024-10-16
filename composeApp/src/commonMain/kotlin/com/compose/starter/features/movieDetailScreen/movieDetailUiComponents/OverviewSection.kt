package com.compose.starter.features.movieDetailScreen.movieDetailUiComponents

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.compose.starter.commonUi.ExpandableText
import com.compose.starter.commonUi.TmdbPosterBottomSheet
import com.compose.starter.features.movieDetailScreen.OverviewPair
import com.compose.starter.screenWidth
import com.compose.starter.spacingsAndBorders.rectBorder
import com.compose.starter.spacingsAndBorders.spacing
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.cancel
import composestarter.composeapp.generated.resources.overview
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewSection(
    title: String?,
    overview: String,
    posterPath: String?,
    importantCrewMap: List<Pair<String, String>>,
    overviewPairs: List<List<OverviewPair>>,
) {
    val scope = rememberCoroutineScope()
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    MovieTitleIconSection(
        title = Res.string.overview,
        endIcon = Icons.Sharp.MoreVert,
        onEndIconClick = {
            scope.launch {
                isBottomSheetVisible = true
                sheetState.expand()
            }
        }
    ) {
        ExpandableText(
            style = MaterialTheme.typography.titleMedium,
            value = overview
        )

        TmdbPosterBottomSheet(
            isBottomSheetVisible = isBottomSheetVisible,
            sheetState = sheetState,
            posterPath = posterPath,
            onDismiss = {
                scope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    isBottomSheetVisible = false
                }
            }
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {

                title?.let {
                    Text(
                        it,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                    )
                }

                Spacer(Modifier.height(MaterialTheme.spacing.small))

                overviewPairs.forEach { pair ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(bottom = MaterialTheme.spacing.small),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        pair.forEachIndexed { index, it ->
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.tiny)
                            ) {
                                Text(
                                    stringResource(it.title),
                                    modifier = Modifier.fillMaxWidth(),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    textAlign = if (index == 0) TextAlign.Start else TextAlign.End,
                                )

                                Text(
                                    it.value ?: "-",
                                    modifier = Modifier.fillMaxWidth(),
                                    style = MaterialTheme.typography.titleSmall,
                                    textAlign = if (index == 0) TextAlign.Start else TextAlign.End,
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    importantCrewMap.forEach {
                        OutlinedCard(
                            modifier = Modifier
                                .width(screenWidth * 0.5f)
                                .fillMaxHeight()
                                .padding(end = MaterialTheme.spacing.small)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(MaterialTheme.spacing.extraSmall),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    it.first,
                                    modifier = Modifier.fillMaxWidth(),
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    textAlign = TextAlign.Center,
                                )
                                Text(
                                    it.second,
                                    modifier = Modifier.fillMaxWidth(),
                                    style = MaterialTheme.typography.labelSmall,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(MaterialTheme.spacing.small))

                OutlinedButton(
                    shape = MaterialTheme.rectBorder.small,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            isBottomSheetVisible = false
                        }
                    }
                ) {
                    Text(
                        stringResource(Res.string.cancel),
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}