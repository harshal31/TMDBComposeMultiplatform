package com.compose.starter.features.castAndCrewScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compose.starter.di.ShareMediaData
import org.koin.compose.koinInject


@Composable
fun CastAndCrewScreen(padding: PaddingValues) {
    val shareMediaData = koinInject<ShareMediaData>()
    val uiState by shareMediaData.movieState.collectAsStateWithLifecycle()

    Column {
        Text("Cast listing")
    }

}