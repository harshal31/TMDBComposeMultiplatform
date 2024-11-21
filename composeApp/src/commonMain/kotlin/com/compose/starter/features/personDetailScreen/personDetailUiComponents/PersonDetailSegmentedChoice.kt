package com.compose.starter.features.personDetailScreen.personDetailUiComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.compose.starter.spacingsAndBorders.rectBorder
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing
import com.compose.starter.utilities.second
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.movie_filled
import composestarter.composeapp.generated.resources.movies
import composestarter.composeapp.generated.resources.tvSeries
import composestarter.composeapp.generated.resources.tv_filled
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun PersonDetailChoiceButtons(modifier: Modifier, onChoiceClick: (SingleChoiceClick) -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {

        FilledTonalButton(
            modifier = Modifier
                .weight(1f)
                .height(ButtonDefaults.MinHeight),
            shape = MaterialTheme.rectBorder.small,
            onClick = {
                onChoiceClick(SingleChoiceClick.MOVIES)
            },
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconBasedOnIndex(0)

                Spacer(modifier = Modifier.width(MaterialTheme.spacing.extraSmall))

                Text(
                    stringResource(segments.first().title),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )
            }
        }

        FilledTonalButton(
            modifier = Modifier
                .weight(1f)
                .height(ButtonDefaults.MinHeight),
            shape = MaterialTheme.rectBorder.small,
            onClick = {
                onChoiceClick(SingleChoiceClick.TV_SERIES)
            },
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                IconBasedOnIndex(1)

                Spacer(modifier = Modifier.width(MaterialTheme.spacing.extraSmall))

                Text(
                    stringResource(segments.second().title),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )
            }
        }
    }
}

@Composable
private fun IconBasedOnIndex(index: Int) {
    val icon = segments[index].icon
    val contentDesc = segments[index].title

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(MaterialTheme.sizing.eighteen),
            painter = painterResource(icon),
            contentDescription = stringResource(contentDesc),
        )
    }
}


@Immutable
private data class PersonDetailChoice(
    val title: StringResource,
    val icon: DrawableResource,
)

private val segments = listOf(
    PersonDetailChoice(
        title = Res.string.movies,
        icon = Res.drawable.movie_filled,
    ),
    PersonDetailChoice(
        title = Res.string.tvSeries,
        icon = Res.drawable.tv_filled,
    )
)

enum class SingleChoiceClick {
    MOVIES, TV_SERIES, NONE;
}