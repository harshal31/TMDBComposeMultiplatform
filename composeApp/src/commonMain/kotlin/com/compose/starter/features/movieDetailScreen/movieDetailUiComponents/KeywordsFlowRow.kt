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
import com.compose.starter.utilities.ImmutableList

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun KeywordsFlowRow(
    modifier: Modifier,
    keywords: ImmutableList<MappedKeyword>
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
    ) {
        keywords.items.forEach {
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