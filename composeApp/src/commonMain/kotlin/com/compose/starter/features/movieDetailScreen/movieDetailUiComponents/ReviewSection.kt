package com.compose.starter.features.movieDetailScreen.movieDetailUiComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.compose.starter.commonUi.AvatarElseInitialLetter
import com.compose.starter.networking.model.MappedReview
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.review_by
import composestarter.composeapp.generated.resources.written_by
import org.jetbrains.compose.resources.stringResource

@Composable
fun ReviewCard(modifier: Modifier, review: MappedReview) {
    Box(
        modifier = Modifier.then(modifier)
    ) {
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.small)
                    .padding(start = MaterialTheme.spacing.small),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
            ) {

                AvatarElseInitialLetter(
                    avatarUrl = review.avatarPath,
                    userName = review.authorName,
                    circleSize = MaterialTheme.sizing.sixty,
                    borderWidth = MaterialTheme.sizing.none
                )

                Column(modifier = Modifier.weight(1f)) {
                    val value = review.authorName ?: review.authorUsername
                    Text(
                        stringResource(Res.string.review_by, value),
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))

                    Text(
                        stringResource(
                            Res.string.written_by,
                            review.authorUsername,
                            review.createdAt
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

                    Text(
                        review.content,
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}