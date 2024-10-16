package com.compose.starter.commonUi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.zIndex
import com.compose.starter.constants.ContentDescription
import com.compose.starter.spacingsAndBorders.rectBorder
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TmdbPosterBottomSheet(
    isBottomSheetVisible: Boolean,
    sheetState: SheetState,
    posterPath: String?,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    val imageWidth = MaterialTheme.sizing.largeTileWidth
    val imageHeight = MaterialTheme.sizing.twoHundred
    val halfImageHeight = imageHeight / 2

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = Color.Transparent,
            dragHandle = {
                BottomSheetDefaults.DragHandle(
                    color = Color.Transparent
                )
            },
            contentColor = MaterialTheme.colorScheme.onSurface,
            windowInsets = WindowInsets(0, 0, 0, 0)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = halfImageHeight)
            ) {
                Box(
                    modifier = Modifier
                        .width(imageWidth)
                        .height(imageHeight)
                        .align(Alignment.TopCenter)
                        .offset(y = -halfImageHeight)
                        .clip(MaterialTheme.shapes.small)
                        .zIndex(1f),
                    contentAlignment = Alignment.Center
                ) {
                    posterPath?.let {
                        TmdbCropSizeImage(
                            modifier = Modifier
                                .width(imageWidth)
                                .height(imageHeight),
                            url = posterPath,
                            contentDescription = ContentDescription.BOTTOM_SHEET_POSTER,
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.rectBorder.topLevelMediumBottomSheetBorder
                        )
                        .padding(top = halfImageHeight)
                        .padding(horizontal = MaterialTheme.spacing.medium),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    content()
                    Spacer(modifier = Modifier.systemBarsPadding())
                }
            }
        }
    }
}

