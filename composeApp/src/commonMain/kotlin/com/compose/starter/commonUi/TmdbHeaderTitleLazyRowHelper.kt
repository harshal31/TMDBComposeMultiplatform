package com.compose.starter.commonUi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import com.compose.starter.constants.ContentDescription
import com.compose.starter.networking.model.TmdbMediaData
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.right_arrow
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun TmdbHeaderTitleWithLazyRow(
    headerTitle: StringResource,
    values: List<TmdbMediaData>,
    goToMoreScreen: (String) -> Unit,
    onItemClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                stringResource(headerTitle),
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.ExtraBold)
            )

            IconButton(
                modifier = Modifier.size(MaterialTheme.sizing.large),
                onClick = {
                    goToMoreScreen(headerTitle.key)
                }
            ) {
                Icon(
                    painterResource(Res.drawable.right_arrow),
                    modifier = Modifier.size(MaterialTheme.sizing.large),
                    contentDescription = ContentDescription.MOVE_TO_NEXT,
                )
            }
        }

        LazyRow(
            modifier = Modifier.height(MaterialTheme.sizing.largeTileHeight),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            items(
                values,
                key = { it.mediaId ?: 0 }
            ) {
                OutlinedCard(
                    onClick = {
                        onItemClick(it.mediaId ?: "")
                    }
                ) {
                    TmdbCropSizeImage(
                        modifier = Modifier.size(
                            MaterialTheme.sizing.largeTileWidth,
                            MaterialTheme.sizing.largeTileHeight,
                        ),
                        url = it.imageUrl ?: "",
                        contentScale = ContentScale.FillHeight
                    )
                }
            }
        }
    }
}
