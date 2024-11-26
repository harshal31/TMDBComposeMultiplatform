package com.compose.starter.features.movieDetailScreen.movieDetailUiComponents

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.compose.starter.commonUi.CoilCropSizeImage
import com.compose.starter.navGraphs.Movie
import com.compose.starter.networking.model.MappedCast
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing
import com.compose.starter.utilities.ImmutableList

@Composable
fun FeaturedCastRow(
    modifier: Modifier,
    casts: ImmutableList<MappedCast>,
    navigateToDetail: (Movie) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .height(IntrinsicSize.Max)
            .horizontalScroll(rememberScrollState())
    ) {
        casts.items.forEach { cast ->
            key(cast.id) {
                OutlinedCard(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(end = MaterialTheme.spacing.small),
                    onClick = {
                        cast.id?.let {
                            navigateToDetail(Movie.CastDetail(it))
                        }
                    }
                ) {
                    Column(
                        modifier = Modifier.width(MaterialTheme.sizing.oneTwenty)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
                    ) {
                        CoilCropSizeImage(
                            modifier = Modifier.size(
                                MaterialTheme.sizing.oneTwenty,
                                MaterialTheme.sizing.oneEighty,
                            ),
                            url = cast.profilePath,
                            contentScale = ContentScale.FillHeight
                        )

                        Text(
                            cast.name,
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center,
                        )

                        Text(
                            cast.character,
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.titleSmall,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}