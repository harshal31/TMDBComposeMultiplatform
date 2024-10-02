package com.compose.starter.navGraphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.compose.starter.features.homeScreen.HomeScreenEvent
import com.compose.starter.features.peopleScreen.PeopleScreen
import com.compose.starter.features.peopleScreen.PeopleScreenViewModel
import com.compose.starter.routes.Screen.Graphs.PeoplesGraph
import com.compose.starter.routes.Screen.Peoples
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.peoplesGraph(
    paddingValues: PaddingValues,
    navController: NavController,
    onEvent: (HomeScreenEvent) -> Unit,
) {
    navigation<PeoplesGraph>(startDestination = Peoples) {
        composable<Peoples> {
            val viewModel = koinViewModel<PeopleScreenViewModel>()
            PeopleScreen(
                padding = paddingValues,
                viewModel = viewModel,
                onEvent = onEvent,
                goToMoreScreen = {},
                onItemClick = {}
            )
        }
    }
}
