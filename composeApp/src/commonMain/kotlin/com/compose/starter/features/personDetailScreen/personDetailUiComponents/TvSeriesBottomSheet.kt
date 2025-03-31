package com.compose.starter.features.personDetailScreen.personDetailUiComponents

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import com.compose.starter.networking.model.MappedMovieCredit
import com.compose.starter.utilities.ImmutableMap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvSeriesBottomSheet(
    sheetState: SheetState,
    isBottomSheetVisible: Boolean,
    tvCredits: ImmutableMap<String, List<MappedMovieCredit>>,
    onDismiss: () -> Unit
) {


    if (isBottomSheetVisible) {

    }

}