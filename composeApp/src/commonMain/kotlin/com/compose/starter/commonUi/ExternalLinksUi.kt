package com.compose.starter.commonUi

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.compose.starter.constants.ContentDescription
import com.compose.starter.openLinkInBrowser
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.facebook
import composestarter.composeapp.generated.resources.imdb
import composestarter.composeapp.generated.resources.instagram
import composestarter.composeapp.generated.resources.twitter
import composestarter.composeapp.generated.resources.web
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ExternalLinksUi(links: List<ExternalLink>) {
    Row(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        links.forEach {
            val (logo, link) = it.redirect()

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(MaterialTheme.sizing.extraLarge)
                    .clickable {
                        openLinkInBrowser(link)
                    }
            ) {
                Image(
                    painter = painterResource(logo),
                    modifier = Modifier.size(MaterialTheme.sizing.extraLarge),
                    contentDescription = ContentDescription.externalLink(link),
                )
            }
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
        }
    }
}

@Immutable
sealed interface ExternalLink {
    fun redirect(): Pair<DrawableResource, String>

    @Immutable
    data class Facebook(private val id: String) : ExternalLink {
        override fun redirect(): Pair<DrawableResource, String> {
            return Res.drawable.facebook to "https://www.facebook.com/$id"
        }
    }

    @Immutable
    data class Twitter(private val id: String) : ExternalLink {
        override fun redirect(): Pair<DrawableResource, String> {
            return Res.drawable.twitter to "https://x.com/$id"
        }
    }

    @Immutable
    data class ImdbMediaProfile(private val id: String) : ExternalLink {
        override fun redirect(): Pair<DrawableResource, String> {
            return Res.drawable.imdb to "https://www.imdb.com/title/$id"
        }
    }

    @Immutable
    data class ImdbPersonProfile(private val id: String) : ExternalLink {
        override fun redirect(): Pair<DrawableResource, String> {
            return Res.drawable.imdb to "https://www.imdb.com/name/$id"
        }
    }

    @Immutable
    data class Instagram(private val id: String) : ExternalLink {
        override fun redirect(): Pair<DrawableResource, String> {
            return Res.drawable.instagram to "https://www.instagram.com/$id"
        }
    }

    @Immutable
    data class HomePage(private val id: String) : ExternalLink {
        override fun redirect(): Pair<DrawableResource, String> {
            return Res.drawable.web to id
        }
    }
}