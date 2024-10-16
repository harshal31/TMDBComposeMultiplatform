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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.compose.starter.networking.model.Keyword
import com.compose.starter.spacingsAndBorders.spacing
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.keywords

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun KeywordsSection(keywords: List<Keyword>) {
    MovieTitleIconSection(
        title = Res.string.keywords,
        isEndSection = true
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
        ) {
            keywords.forEach {
                SuggestionChip(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(end = MaterialTheme.spacing.extraSmall),
                    onClick = {},
                    label = {
                        Text(
                            it.name ?: "-",
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.Center,
                        )
                    }
                )
            }
        }
    }
}