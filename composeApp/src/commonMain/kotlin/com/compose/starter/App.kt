package com.compose.starter

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.compose.starter.features.homeScreen.HomeScreen
import com.compose.starter.features.homeScreen.HomeScreenViewModel
import com.compose.starter.features.loginScreen.LoginScreen
import com.compose.starter.features.loginScreen.LoginScreenViewModel
import com.compose.starter.localData.LocalStore
import com.compose.starter.routes.Screen
import com.compose.starter.theme.TMDBTheme
import kotlinx.coroutines.flow.first
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val koin = getKoin()
    var screen by remember { mutableStateOf<Any>(Screen.LoginScreen) }
    LaunchedEffect(Unit) {
        if ((koin.get<LocalStore>().getString(LocalStore.SESSION_ID).first() ?: "").isNotEmpty()) {
            screen = Screen.HomeScreen
        }
    }
    TMDBTheme {
        val navController = rememberNavController()
        NavHost(
            navController,
            startDestination = screen,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            },
        ) {
            composable<Screen.LoginScreen> {
                val viewModel = koinViewModel<LoginScreenViewModel>()
                LoginScreen(viewModel) {
                    navController.navigate(Screen.HomeScreen) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }

            composable<Screen.HomeScreen> {
                val viewModel = koinViewModel<HomeScreenViewModel>()
                HomeScreen(viewModel)
            }
        }
    }
}


