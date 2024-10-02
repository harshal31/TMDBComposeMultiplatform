package com.compose.starter

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import com.compose.starter.appInitializations.AppInitialData
import com.compose.starter.appInitializations.AppLevelViewModel
import com.compose.starter.di.getKoinValue
import com.compose.starter.di.imageLoader
import com.compose.starter.features.homeScreen.HomeScreen
import com.compose.starter.features.homeScreen.HomeScreenViewModel
import com.compose.starter.features.loginScreen.LoginScreen
import com.compose.starter.features.loginScreen.LoginScreenViewModel
import com.compose.starter.features.settingsScreen.ThemeMode
import com.compose.starter.localData.LocalStore
import com.compose.starter.routes.Screen
import com.compose.starter.spacingsAndBorders.CircularBorder
import com.compose.starter.spacingsAndBorders.LocalCircularBorder
import com.compose.starter.spacingsAndBorders.LocalRectBorder
import com.compose.starter.spacingsAndBorders.LocalSizing
import com.compose.starter.spacingsAndBorders.LocalSpacing
import com.compose.starter.spacingsAndBorders.RectangularBorder
import com.compose.starter.spacingsAndBorders.Sizing
import com.compose.starter.spacingsAndBorders.Spacing
import com.compose.starter.theme.TMDBTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App(initialData: AppInitialData?) {
    val defaultTheme = isSystemInDarkTheme()
    val appModel = koinViewModel<AppLevelViewModel>()

    val initialScreen: Any =
        if (initialData?.session?.isEmpty() == true) Screen.LoginScreen else Screen.HomeScreen

    val appLevelThemeState by appModel.themState.collectAsStateWithLifecycle(initialValue = null)

    val isDarkTheme by remember {
        mutableStateOf(
            initialData?.isDarkTheme ?: defaultTheme
        )
    }

    LaunchedEffect(Unit) {
        getKoinValue<LocalStore>().putString(
            LocalStore.THEME,
            initialData?.isDarkThemeValue(isDarkTheme)
        )
    }

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalRectBorder provides RectangularBorder(),
        LocalCircularBorder provides CircularBorder(),
        LocalSizing provides Sizing()
    ) {
        TMDBTheme(darkTheme = ThemeMode.isDarkTheme(appLevelThemeState) ?: isDarkTheme) {
            setSingletonImageLoaderFactory {
                imageLoader(it)
            }
            val navController = rememberNavController()
            NavHost(
                navController,
                startDestination = initialScreen,
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
}
