package com.compose.starter.features.personDetailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compose.starter.commonUi.CircleIcon
import com.compose.starter.commonUi.CleanContent
import com.compose.starter.commonUi.CoilCropSizeImage
import com.compose.starter.features.personDetailScreen.personDetailUiComponents.AboutPersonInformation
import com.compose.starter.features.personDetailScreen.personDetailUiComponents.MoviesBottomSheet
import com.compose.starter.features.personDetailScreen.personDetailUiComponents.PersonDetailChoiceButtons
import com.compose.starter.features.personDetailScreen.personDetailUiComponents.PersonInformation
import com.compose.starter.features.personDetailScreen.personDetailUiComponents.ProfileSection
import com.compose.starter.features.personDetailScreen.personDetailUiComponents.SingleChoiceClick
import com.compose.starter.features.personDetailScreen.personDetailUiComponents.TvSeriesBottomSheet
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing
import com.compose.starter.utilities.hideBottomSheet
import com.compose.starter.utilities.showBottomSheet
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.arrow_back
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailScreen(
    personId: Int,
    viewModel: PersonDetailViewModel,
    navigateToMovieDetail: (String) -> Unit,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    var isMoviesBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    var isTvSeriesBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val moviesSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val tvSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LaunchedEffect(Unit) {
        viewModel.fetchPersonDetail(personId)
    }

    Scaffold { padding ->
        CleanContent(padding = padding, apiState = uiState.apiState) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(MaterialTheme.spacing.default)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = ButtonDefaults.MinHeight.plus(MaterialTheme.spacing.small)),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                ) {
                    item {
                        PersonAvatarImage(
                            avatarPath = uiState.avatarPath,
                            personName = uiState.personName,
                        )
                    }

                    item {
                        PersonInformation(
                            personName = uiState.personName,
                            gender = uiState.gender,
                            externalLinks = uiState.externalLinks
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                    }

                    item {
                        AboutPersonInformation(
                            bornOn = uiState.bornOn,
                            birthPlace = uiState.birthPlace,
                            years = uiState.years ?: 0,
                            knownFor = uiState.knownFor,
                            bio = uiState.bio,
                        )
                    }

                    item {
                        ProfileSection(uiState.profileImages)
                    }

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CircleIcon(
                        icon = Res.drawable.arrow_back,
                        onIconClick = onBack
                    )
                }

                PersonDetailChoiceButtons(
                    modifier = Modifier.align(Alignment.BottomStart)
                ) {
                    when (it) {
                        SingleChoiceClick.MOVIES -> {
                            scope.showBottomSheet(tvSheetState) { isVisible ->
                                isMoviesBottomSheetVisible = isVisible
                            }
                        }

                        SingleChoiceClick.TV_SERIES -> {
                            scope.showBottomSheet(tvSheetState) { isVisible ->
                                isTvSeriesBottomSheetVisible = isVisible
                            }
                        }

                        SingleChoiceClick.NONE -> Unit
                    }
                }
            }

            MoviesBottomSheet(
                sheetState = moviesSheetState,
                isBottomSheetVisible = isMoviesBottomSheetVisible,
                movieCredits = uiState.movieCredits,
                navigateToMovieRole = navigateToMovieDetail,
                onDismiss = {
                    scope.hideBottomSheet(moviesSheetState) {
                        isMoviesBottomSheetVisible = it
                    }
                }
            )
            TvSeriesBottomSheet(
                sheetState = tvSheetState,
                isBottomSheetVisible = isTvSeriesBottomSheetVisible,
                tvCredits = uiState.movieCredits,
                onDismiss = {
                    scope.hideBottomSheet(tvSheetState) {
                        isTvSeriesBottomSheetVisible = it
                    }
                }
            )
        }
    }
}

@Composable
@Preview
private fun PersonAvatarImage(
    avatarPath: String?,
    personName: String?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        ElevatedCard(
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = MaterialTheme.sizing.twelve,
                focusedElevation = MaterialTheme.sizing.twelve,
            ), modifier = Modifier.size(
                width = MaterialTheme.sizing.twoHundred,
                height = MaterialTheme.sizing.threeHundred
            )
        ) {
            CoilCropSizeImage(
                modifier = Modifier.fillMaxSize(),
                url = avatarPath ?: "",
                contentScale = ContentScale.FillHeight,
                contentDescription = personName ?: ""
            )
        }
    }
}