package com.compose.starter.features.movieDetailScreen.movieDetailUiComponents

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.compose.starter.commonUi.CircleIcon
import com.compose.starter.commonUi.CoilCropSizeImage
import com.compose.starter.commonUi.CoilImage
import com.compose.starter.commonUi.DetailTitleWithIcon
import com.compose.starter.constants.AppConstants
import com.compose.starter.constants.ContentDescription
import com.compose.starter.networking.model.MappedBackdrops
import com.compose.starter.networking.model.MappedImagePoster
import com.compose.starter.networking.model.MappedVideo
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing
import com.compose.starter.theme.mediaDetailFillColor
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.backdrops
import composestarter.composeapp.generated.resources.media
import composestarter.composeapp.generated.resources.play
import composestarter.composeapp.generated.resources.posters
import composestarter.composeapp.generated.resources.videos
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MediaSection(
    modifier: Modifier,
    videos: List<MappedVideo>?,
    posters: List<MappedImagePoster>?,
    backdrops: List<MappedBackdrops>?,
    onTabItemDataClick: (TabItemDataClick) -> Unit,
) {
    val tabs = listOf(
        if (videos.isNullOrEmpty().not()) TabItem(
            title = Res.string.videos,
            pos = 0
        ) else null,
        if (posters.isNullOrEmpty().not()) TabItem(
            title = Res.string.posters,
            pos = 1
        ) else null,
        if (backdrops.isNullOrEmpty().not()) TabItem(
            title = Res.string.backdrops,
            pos = 2
        ) else null,
    ).mapNotNull { it }

    var currentTabIndex by remember { mutableIntStateOf(0) }

    val pagerState = rememberPagerState { tabs.size }

    LaunchedEffect(key1 = currentTabIndex) {
        pagerState.animateScrollToPage(currentTabIndex)
    }

    LaunchedEffect(key1 = pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            currentTabIndex = pagerState.currentPage
        }
    }

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .then(modifier),
    ) {
        DetailTitleWithIcon(
            title = stringResource(Res.string.media),
            endIcon = Icons.Sharp.MoreVert,
            onEndIconClick = {
                when (val pos = tabs[currentTabIndex].pos) {
                    0 -> onTabItemDataClick(
                        TabItemDataClick.TabVideos(
                            clickItemPos = pos,
                            videos = videos ?: emptyList()
                        )
                    )

                    1 -> onTabItemDataClick(
                        TabItemDataClick.TabPosters(
                            clickItemPos = pos,
                            posters = posters ?: emptyList()
                        )
                    )

                    2 -> onTabItemDataClick(
                        TabItemDataClick.TabBackdrops(
                            clickItemPos = pos,
                            backdrops = backdrops ?: emptyList()
                        )
                    )

                    else -> {}
                }
            }
        )

        TabRow(
            selectedTabIndex = currentTabIndex,
            modifier = Modifier.padding(MaterialTheme.spacing.none)
        ) {
            tabs.forEachIndexed { index, tabItem ->
                Tab(
                    selected = index == currentTabIndex,
                    unselectedContentColor = MaterialTheme.colorScheme.onBackground,
                    onClick = {
                        currentTabIndex = index
                    },
                    text = {
                        Text(
                            text = stringResource(tabItem.title),
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = MaterialTheme.spacing.small)
        ) {
            when (tabs[currentTabIndex].pos) {
                0 -> MediaVideos(videos ?: emptyList())
                1 -> MediaPosters(posters ?: emptyList())
                2 -> MediaBackdrops(backdrops ?: emptyList())
                else -> {}
            }
        }
    }
}

@Composable
private fun MediaVideos(results: List<MappedVideo>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        results.take(9).forEach {
            key(it.key) {
                Column(modifier = Modifier.fillMaxHeight()) {
                    Box(
                        modifier = Modifier
                            .size(
                                width = MaterialTheme.sizing.twoSeventy,
                                height = MaterialTheme.sizing.largeTileWidth
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        OutlinedCard(
                            modifier = Modifier
                                .size(
                                    width = MaterialTheme.sizing.twoSeventy,
                                    height = MaterialTheme.sizing.largeTileWidth
                                )
                        ) {
                            CoilImage(
                                modifier = Modifier.fillMaxSize(),
                                url = AppConstants.youtubeThumbnailImage(it.key),
                                scale = ContentScale.FillBounds,
                                contentDesc = ContentDescription.contentImage(it.name)
                            )
                        }

                        CircleIcon(
                            icon = Res.drawable.play,
                            contentDesc = ContentDescription.PLAY,
                            tint = mediaDetailFillColor,
                            onIconClick = {}
                        )
                    }

                    Text(
                        it.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .width(MaterialTheme.sizing.twoSeventy),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun MediaPosters(posters: List<MappedImagePoster>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        posters.take(9).forEach {
            key(it.filePath) {
                OutlinedCard(
                    modifier = Modifier
                        .size(
                            width = MaterialTheme.sizing.largeTileWidth,
                            height = MaterialTheme.sizing.twoHundred
                        )
                ) {
                    CoilCropSizeImage(
                        modifier = Modifier.fillMaxSize(),
                        url = it.filePath,
                        contentScale = ContentScale.Crop,
                        contentDescription = ContentDescription.contentImage(it.filePath)
                    )
                }
            }
        }
    }
}

@Composable
private fun MediaBackdrops(backdrops: List<MappedBackdrops>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        backdrops.take(9).forEach {
            key(it.filePath) {
                OutlinedCard(
                    modifier = Modifier
                        .size(
                            width = MaterialTheme.sizing.twoFifty,
                            height = MaterialTheme.sizing.largeTileWidth
                        )
                ) {
                    CoilCropSizeImage(
                        modifier = Modifier.fillMaxSize(),
                        url = it.filePath,
                        contentScale = ContentScale.FillBounds,
                        contentDescription = ContentDescription.contentImage(it.filePath)
                    )
                }
            }
        }
    }
}

sealed interface TabItemDataClick {
    data class TabVideos(
        val clickItemPos: Int,
        val videos: List<MappedVideo>,
        val type: String = "Videos",
    ) : TabItemDataClick

    data class TabPosters(
        val clickItemPos: Int,
        val posters: List<MappedImagePoster>,
        val type: String = "Posters",
    ) : TabItemDataClick

    data class TabBackdrops(
        val clickItemPos: Int,
        val backdrops: List<MappedBackdrops>,
        val type: String = "Backdrops",
    ) : TabItemDataClick
}

private data class TabItem(val title: StringResource, val pos: Int)


//.basicMarquee()  we can used if we scroll text automatically horizontally if