package com.compose.starter.navGraphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.compose.starter.features.peopleScreen.PeopleScreenViewModel
import com.compose.starter.features.peopleScreen.PersonScreen
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.personsGraph(navController: NavController) {
    navigation<Person.Graph>(startDestination = Person.Persons) {
        composable<Person.Persons> {
            val viewModel = koinViewModel<PeopleScreenViewModel>()
            PersonScreen(
                viewModel = viewModel,
                goToMoreScreen = {},
                onItemClick = {}
            )
        }
    }
}

@Serializable
sealed interface Person {
    @Serializable
    data object Graph: Person
    @Serializable
    data object Persons: Person
}

