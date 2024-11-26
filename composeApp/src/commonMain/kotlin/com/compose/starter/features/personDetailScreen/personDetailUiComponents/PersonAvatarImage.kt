package com.compose.starter.features.personDetailScreen.personDetailUiComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.compose.starter.commonUi.CoilCropSizeImage
import com.compose.starter.spacingsAndBorders.sizing
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun PersonAvatarImage(
    avatarPath: String?,
    personName: String?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        ElevatedCard(
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = MaterialTheme.sizing.twelve,
                focusedElevation = MaterialTheme.sizing.twelve,
            ), modifier = Modifier.size(
                width = MaterialTheme.sizing.twoHundred,
                height = MaterialTheme.sizing.threeHundred
            )
        ) {
            CoilCropSizeImage(
                modifier = Modifier.fillMaxSize(),
                url = avatarPath ?: "",
                contentScale = ContentScale.FillHeight,
                contentDescription = personName ?: ""
            )
        }
    }
}