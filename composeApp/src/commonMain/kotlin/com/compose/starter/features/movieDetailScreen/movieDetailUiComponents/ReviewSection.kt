package com.compose.starter.features.movieDetailScreen.movieDetailUiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.compose.starter.commonUi.CoilImage
import com.compose.starter.constants.AppConstants
import com.compose.starter.constants.ContentDescription
import com.compose.starter.networking.model.ReviewResult
import com.compose.starter.spacingsAndBorders.circleBorder
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing
import com.compose.starter.utilities.formatDate
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.people_filled
import composestarter.composeapp.generated.resources.review_by
import composestarter.composeapp.generated.resources.reviews
import composestarter.composeapp.generated.resources.written_by
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ReviewSection(review: ReviewResult) {
    MovieTitleIconSection(
        title = Res.string.reviews,
        endIcon = Icons.Sharp.MoreVert,
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
                CoilImage(
                    modifier = Modifier
                        .size(MaterialTheme.sizing.sixty)
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                            MaterialTheme.circleBorder.extraLarge
                        ),
                    url = AppConstants.FULL_SIZE_BASE_URL + review.authorDetails?.avatarPath,
                    contentDesc = ContentDescription.PROFILE_IMAGE,
                    scale = ContentScale.Crop,
                    placeHolder = painterResource(Res.drawable.people_filled),
                    errorPlaceholder = painterResource(Res.drawable.people_filled),
                )

                Column(modifier = Modifier.weight(1f)) {
                    val value = review.authorDetails?.name ?: review.authorDetails?.username ?: "-"
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
                            (review.authorDetails?.username ?: "-"),
                            (review.createdAt.formatDate())
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

                    Text(
                        review.content ?: "",
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