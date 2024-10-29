package com.compose.starter.features.personDetailScreen.personDetailUiComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import com.compose.starter.commonUi.CoilCropSizeImage
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.profiles
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProfileSection(profiles: List<String>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
    ) {

        Text(
            stringResource(Res.string.profiles),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.sizing.oneSeventy),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            items(profiles, key = { it }) {
                OutlinedCard(
                    onClick = {

                    }
                ) {
                    CoilCropSizeImage(
                        modifier = Modifier.size(
                            MaterialTheme.sizing.mediumImageSize,
                            MaterialTheme.sizing.oneEighty,
                        ),
                        url = it,
                        contentScale = ContentScale.FillHeight
                    )
                }
            }
        }
    }
}