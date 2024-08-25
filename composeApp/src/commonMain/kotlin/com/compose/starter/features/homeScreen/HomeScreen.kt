package com.compose.starter.features.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun HomeScreen(viewModel: HomeScreenViewModel) {
    Column(verticalArrangement = Arrangement.Center) {
        Text("Detail Screen")
    }
}
