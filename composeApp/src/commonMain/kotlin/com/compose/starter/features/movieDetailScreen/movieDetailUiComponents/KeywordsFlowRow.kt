package com.compose.starter.features.movieDetailScreen.movieDetailUiComponents

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.compose.starter.networking.model.MappedKeyword
import com.compose.starter.spacingsAndBorders.spacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun KeywordsFlowRow(keywords: List<MappedKeyword>) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.default),
    ) {
        keywords.forEach {
            key(it.id) {
                SuggestionChip(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(end = MaterialTheme.spacing.extraSmall),
                    onClick = {},
                    label = {
                        Text(
                            it.name,
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.Center,
                        )
                    }
                )
            }
        }
    }
}