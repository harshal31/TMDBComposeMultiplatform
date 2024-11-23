package com.compose.starter.features.personDetailScreen.personDetailUiComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.compose.starter.commonUi.CoilDrawableImage
import com.compose.starter.commonUi.ExpandableText
import com.compose.starter.commonUi.ExternalLink
import com.compose.starter.commonUi.ExternalLinksUi
import com.compose.starter.networking.model.GenderIdentifier
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing
import com.compose.starter.utilities.ImmutableList
import com.compose.starter.utilities.pluralResource
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.about
import composestarter.composeapp.generated.resources.birthPlace
import composestarter.composeapp.generated.resources.bornOn
import composestarter.composeapp.generated.resources.knownFor
import composestarter.composeapp.generated.resources.year
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun PersonInformation(
    personName: String?,
    gender: Int?,
    externalLinks: ImmutableList<ExternalLink>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
    ) {
        if (personName.isNullOrEmpty().not()) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    personName ?: "",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )
                CoilDrawableImage(
                    modifier = Modifier.size(MaterialTheme.sizing.extraLarge),
                    drawableName = GenderIdentifier.gender(gender),
                )
            }
        }

        if (externalLinks.items.isNotEmpty()) {
            ExternalLinksUi(externalLinks)
        }
    }
}


@Composable
fun AboutPersonInformation(
    bornOn: String?,
    knownFor: String?,
    birthPlace: String?,
    years: Int,
    bio: String?
) {
    val birthday = buildString {
        if (bornOn.isNullOrEmpty().not()) {
            append(bornOn)
            append(" ")
            append(pluralResource(Res.string.year to Res.plurals.year, years, arrayOf(years)))
        }
    }

    val aboutPairs by remember {
        derivedStateOf {
            listOf(
                Res.string.knownFor to knownFor,
                Res.string.bornOn to birthday,
                Res.string.birthPlace to birthPlace,
            ).mapNotNull {
                if (it.second.isNullOrEmpty().not()) {
                    it.first to it.second!!
                } else null
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            stringResource(Res.string.about),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
        )

        if (aboutPairs.isNotEmpty()) {
            aboutPairs.forEach { (title, subtitle) ->
                TitleWithIcon(
                    title = stringResource(title),
                    value = subtitle
                )
            }
        }

        if (bio.isNullOrEmpty().not()) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medLarge))
            ExpandableText(
                style = MaterialTheme.typography.titleMedium.copy(
                    fontStyle = FontStyle.Italic,
                ),
                value = bio ?: "",
                maxLines = 5
            )
        }
    }
}

@Composable
private fun TitleWithIcon(
    title: String,
    value: String?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            modifier = Modifier.fillMaxHeight(),
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Normal
            ),
        )

        Text(
            value ?: "",
            modifier = Modifier.weight(1f).fillMaxHeight(),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
            ),
        )
    }
}


@Composable
@Preview
private fun PersonInformationPreview() {
    TitleWithIcon("sajvbdka", "sadas")
}
