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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.compose.starter.commonUi.TmdbCropSizeImage
import com.compose.starter.navGraphs.MovieNavigation
import com.compose.starter.networking.model.Cast
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.featured_casts

@Composable
fun TopBilledCastsSection(
    casts: List<Cast>,
    navigateToDetail: (MovieNavigation) -> Unit,
) {
    MovieTitleIconSection(
        title = Res.string.featured_casts,
        endIcon = Icons.Sharp.MoreVert,
        onEndIconClick = {
            navigateToDetail(MovieNavigation.CastAndCrewListScreen)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .horizontalScroll(rememberScrollState())
        ) {
            casts.forEach { cast ->
                OutlinedCard(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(end = MaterialTheme.spacing.small),
                    onClick = {
                        cast.id?.let {
                            navigateToDetail(MovieNavigation.PeopleScreen(it))
                        }
                    }
                ) {
                    Column(
                        modifier = Modifier.width(MaterialTheme.sizing.largeTileWidth)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
                    ) {
                        TmdbCropSizeImage(
                            modifier = Modifier.size(
                                MaterialTheme.sizing.largeTileWidth,
                                MaterialTheme.sizing.oneEighty,
                            ),
                            url = cast.profilePath ?: "",
                            contentScale = ContentScale.Crop
                        )

                        cast.name?.let {
                            Text(
                                it,
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                textAlign = TextAlign.Center,
                            )
                        }

                        cast.character?.let {
                            Text(
                                it,
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.titleSmall,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
    }
}
