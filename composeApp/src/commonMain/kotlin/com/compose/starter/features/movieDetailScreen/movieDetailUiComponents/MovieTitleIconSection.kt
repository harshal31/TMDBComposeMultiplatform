package com.compose.starter.features.movieDetailScreen.movieDetailUiComponents

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import com.compose.starter.commonUi.TmdbDivider
import com.compose.starter.commonUi.TmdbIcon
import com.compose.starter.constants.ContentDescription
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MovieTitleIconSection(
    title: StringResource,
    endIcon: Any? = null,
    onEndIconClick: (() -> Unit)? = null,
    isEndSection: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .animateContentSize()
            .padding(horizontal = MaterialTheme.spacing.default)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(title),
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

        this.content()
        if (isEndSection.not()) {
            TmdbDivider(
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium),
                isVertical = false,
            )
        }
    }
}
