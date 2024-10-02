package com.compose.starter.features.settingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compose.starter.commonUi.CleanContent
import com.compose.starter.commonUi.CoilImage
import com.compose.starter.commonUi.getCountryInfoByCode
import com.compose.starter.commonUi.pairItemVisibility
import com.compose.starter.commonUi.stringItemVisibility
import com.compose.starter.constants.AppConstants
import com.compose.starter.constants.ContentDescription
import com.compose.starter.features.homeScreen.HomeScreenEvent
import com.compose.starter.spacingsAndBorders.circleBorder
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing
import com.compose.starter.theme.avatarBack
import com.compose.starter.theme.avatarFront
import com.compose.starter.utilities.secondOrNull
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.country
import composestarter.composeapp.generated.resources.name
import composestarter.composeapp.generated.resources.theme
import composestarter.composeapp.generated.resources.username
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsScreen(
    padding: PaddingValues,
    onEvent: (HomeScreenEvent) -> Unit,
    viewModel: SettingsScreenViewModel,
) {
    LaunchedEffect(Unit) {
        onEvent(HomeScreenEvent.ShowAppBar)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CleanContent(
        padding,
        uiState.apiState
    ) { pad ->
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(pad)
                .padding(MaterialTheme.spacing.default),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
        ) {
            item {
                UserAvatarImage(uiState)
            }

            stringItemVisibility(value = uiState.accountDetail?.name) {
                HeaderWithValue(stringResource(Res.string.name), it)
            }

            stringItemVisibility(value = uiState.accountDetail?.username) {
                HeaderWithValue(stringResource(Res.string.username), it)
            }

            pairItemVisibility(value = getCountryInfoByCode(uiState.accountDetail?.iso31661)) {
                HeaderWithCountryValue(stringResource(Res.string.country), it)
            }

            item {
                HeaderWithThemeValue(
                    stringResource(Res.string.theme),
                    uiState.theme,
                    viewModel::updateTheme
                )
            }
        }
    }
}

@Composable
private fun HeaderWithValue(
    header: String,
    value: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.default),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
    ) {
        Text(
            header,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            ),
        )

        Text(
            value,
            style = MaterialTheme.typography.titleMedium,
        )

        HorizontalDivider(
            color = MaterialTheme.colorScheme.surfaceContainerHighest,
            thickness = MaterialTheme.sizing.tiny,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun HeaderWithCountryValue(
    header: String,
    value: Pair<String, String>,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.default),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
    ) {
        Text(
            header,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                value.second,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                value.first,
                style = MaterialTheme.typography.headlineSmall,
            )
        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.surfaceContainerHighest,
            thickness = MaterialTheme.sizing.tiny,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun HeaderWithThemeValue(
    header: String,
    value: ThemeMode,
    onUpdateTheme: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.default),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
    ) {
        Text(
            header,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )

        when (value) {
            is ThemeMode.Dark -> ThemeRowValue(
                stringResource(value.stringRes),
                value.value,
                value.res,
                onUpdateTheme
            )

            is ThemeMode.Light -> ThemeRowValue(
                stringResource(value.stringRes),
                value.value,
                value.res,
                onUpdateTheme
            )

            ThemeMode.None -> {}
        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.surfaceContainerHighest,
            thickness = MaterialTheme.sizing.tiny,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ThemeRowValue(
    value: String,
    normalValue: String,
    icon: DrawableResource,
    onUpdateTheme: (String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            value,
            style = MaterialTheme.typography.titleMedium,
        )

        IconButton(
            modifier = Modifier.size(MaterialTheme.sizing.large),
            onClick = { onUpdateTheme(normalValue) }
        ) {
            Icon(
                painterResource(icon),
                modifier = Modifier.size(MaterialTheme.sizing.large),
                contentDescription = value,
            )
        }
    }
}

@Composable
private fun UserAvatarImage(uiState: SettingScreenUiState) {
    val split = uiState.accountDetail?.name
        ?.split(" ")
        ?.mapNotNull { it.uppercase().firstOrNull() }

    val userInitial =
        (split?.firstOrNull()?.toString() ?: "").plus((split?.secondOrNull()?.toString() ?: ""))

    Row(
        Modifier.fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(MaterialTheme.sizing.largeImageSize)
                .background(
                    color = avatarBack,
                    shape = MaterialTheme.circleBorder.extraLarge
                )
        ) {
            uiState.accountDetail?.avatar?.tmdb?.avatarPath?.let {
                CoilImage(
                    modifier = Modifier
                        .size(MaterialTheme.sizing.mediumImageSize)
                        .clip(MaterialTheme.circleBorder.extraLarge),
                    url = AppConstants.CROP_SIZE_BASE_URL + it,
                    contentDesc = ContentDescription.PROFILE_IMAGE,
                    scale = ContentScale.Crop,
                )

            } ?: run {
                Box(
                    modifier = Modifier
                        .size(MaterialTheme.sizing.mediumImageSize)
                        .background(
                            color = avatarFront,
                            shape = MaterialTheme.circleBorder.extraLarge
                        )
                        .align(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {

                    if (userInitial.isNotEmpty()) {
                        Text(
                            userInitial,
                            style = MaterialTheme.typography.displayLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = (MaterialTheme.typography.displayLarge.fontSize.value + 13f).sp
                            ),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}