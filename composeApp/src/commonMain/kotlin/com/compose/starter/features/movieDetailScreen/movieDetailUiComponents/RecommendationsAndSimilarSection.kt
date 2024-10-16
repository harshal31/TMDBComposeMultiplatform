package com.compose.starter.features.movieDetailScreen.movieDetailUiComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.compose.starter.commonUi.TmdbCropSizeImage
import com.compose.starter.networking.model.RecommendationList
import com.compose.starter.networking.model.SimilarResult
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.recommendations
import composestarter.composeapp.generated.resources.similar

@Composable
fun RecommendationsSection(recommendationLists: List<RecommendationList>) {
    MovieTitleIconSection(
        title = Res.string.recommendations
    ) {
        LazyRow(
            modifier = Modifier.height(MaterialTheme.sizing.largeTileHeight),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            items(
                recommendationLists,
                key = { it.id ?: 0 }
            ) {
                OutlinedCard(
                    onClick = {

                    }
                ) {
                    TmdbCropSizeImage(
                        modifier = Modifier.size(
                            MaterialTheme.sizing.largeTileWidth,
                            MaterialTheme.sizing.largeTileHeight,
                        ),
                        url = it.posterPath ?: "",
                        contentScale = ContentScale.FillHeight
                    )
                }
            }
        }
    }
}

@Composable
fun SimilarSection(recommendationLists: List<SimilarResult>) {
    MovieTitleIconSection(
        title = Res.string.similar
    ) {
        LazyRow(
            modifier = Modifier.height(MaterialTheme.sizing.largeTileHeight),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            items(
                recommendationLists,
                key = { it.id ?: 0 }
            ) {
                OutlinedCard(
                    onClick = {

                    }
                ) {
                    TmdbCropSizeImage(
                        modifier = Modifier.size(
                            MaterialTheme.sizing.largeTileWidth,
                            MaterialTheme.sizing.largeTileHeight,
                        ),
                        url = it.posterPath ?: "",
                        contentScale = ContentScale.FillHeight
                    )
                }
            }
        }
    }
}