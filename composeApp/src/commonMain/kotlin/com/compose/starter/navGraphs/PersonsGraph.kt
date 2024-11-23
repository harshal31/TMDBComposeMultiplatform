package com.compose.starter.navGraphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.compose.starter.features.peopleScreen.PersonScreen
import com.compose.starter.features.peopleScreen.PersonScreenViewModel
import com.compose.starter.features.personDetailScreen.PersonDetailScreen
import com.compose.starter.features.personDetailScreen.PersonDetailViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.personsGraph(navController: NavController) {
    navigation<Person.Graph>(startDestination = Person.Persons) {
        composable<Person.Persons> {
            val viewModel = koinViewModel<PersonScreenViewModel>()
            PersonScreen(
                viewModel = viewModel,
                goToMoreScreen = {},
                onItemClick = {
                    navController.navigate(Person.Detail(it.toInt()))
                }
            )
        }
    }

    composable<Person.Detail> {
        val shareData = it.toRoute<Person.Detail>()
        val viewModel = koinViewModel<PersonDetailViewModel>()
        PersonDetailScreen(
            personId = shareData.personId,
            viewModel = viewModel,
            navigateToMovieDetail = { id ->
                navController.navigate(Movie.Detail(id))
            },
            onBack = { navController.navigateUp() }
        )
    }
}

@Serializable
sealed interface Person {
    @Serializable
    data object Graph : Person

    @Serializable
    data object Persons : Person

    @Serializable
    data class Detail(val personId: Int) : Person
}

