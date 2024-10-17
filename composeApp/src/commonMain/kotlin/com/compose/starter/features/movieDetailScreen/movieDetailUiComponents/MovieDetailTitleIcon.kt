package com.compose.starter.features.movieDetailScreen.movieDetailUiComponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.compose.starter.commonUi.TmdbIcon
import com.compose.starter.constants.ContentDescription
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing

@Composable
fun MovieDetailTitleIcon(
    modifier: Modifier = Modifier,
    title: String,
    endIcon: Any? = null,
    onEndIconClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .wrapContentHeight()
            .padding(bottom = MaterialTheme.spacing.extraSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            modifier = Modifier
                .weight(1f)
                .padding(end = MaterialTheme.sizing.small),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
        )

        endIcon?.let {
            TmdbIcon(
                it,
                modifier = Modifier.size(MaterialTheme.sizing.large),
                contentDesc = ContentDescription.INFO,
            ) {
                onEndIconClick?.invoke()
            }
        }
    }
}
