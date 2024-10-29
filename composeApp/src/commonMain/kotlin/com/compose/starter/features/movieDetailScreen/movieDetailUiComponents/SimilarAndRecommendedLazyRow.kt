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
import com.compose.starter.commonUi.CoilCropSizeImage
import com.compose.starter.networking.model.MappedRecommended
import com.compose.starter.networking.model.MappedSimilar
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing


@Composable
fun RecommendedLazyRow(
    modifier: Modifier,
    recommendations: List<MappedRecommended>
) {
    LazyRow(
        modifier = Modifier
            .height(MaterialTheme.sizing.largeTileHeight)
            .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        items(
            recommendations,
            key = { it.id ?: 0 }
        ) {
            OutlinedCard(
                onClick = {

                }
            ) {
                CoilCropSizeImage(
                    modifier = Modifier.size(
                        MaterialTheme.sizing.largeTileWidth,
                        MaterialTheme.sizing.largeTileHeight,
                    ),
                    url = it.posterPath,
                    contentScale = ContentScale.FillHeight
                )
            }
        }
    }
}


@Composable
fun SimilarLazyRow(
    modifier: Modifier,
    similars: List<MappedSimilar>
) {
    LazyRow(
        modifier = Modifier
            .height(MaterialTheme.sizing.largeTileHeight)
            .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        items(
            similars,
            key = { it.id ?: 0 }
        ) {
            OutlinedCard(
                onClick = {

                }
            ) {
                CoilCropSizeImage(
                    modifier = Modifier.size(
                        MaterialTheme.sizing.largeTileWidth,
                        MaterialTheme.sizing.largeTileHeight,
                    ),
                    url = it.posterPath,
                    contentScale = ContentScale.FillHeight
                )
            }
        }
    }
}