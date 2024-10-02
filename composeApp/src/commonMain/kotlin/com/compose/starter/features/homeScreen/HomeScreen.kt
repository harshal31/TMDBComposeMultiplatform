package com.compose.starter.features.homeScreen

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.compose.starter.commonUi.TabView
import com.compose.starter.commonUi.statusNavModifier
import com.compose.starter.constants.ContentDescription
import com.compose.starter.navGraphs.moviesGraph
import com.compose.starter.navGraphs.peoplesGraph
import com.compose.starter.navGraphs.settingsGraph
import com.compose.starter.navGraphs.tvSeriesGraph
import com.compose.starter.routes.Screen
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing
import com.compose.starter.theme.appLogoGradientColor
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.tmdb_logo
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun HomeScreen(homeScreenModel: HomeScreenViewModel) {
    val navController = rememberNavController()
    val uiState by homeScreenModel.uiState.collectAsStateWithLifecycle()
    SideEffect { Napier.d("recomposing the image again") }

    Scaffold(
        modifier = statusNavModifier(statusBarColor = uiState.statusBarColor),
        topBar = {
            AnimatedVisibility(
                visible = uiState.shouldDisplayAppBar,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                TopAppBar(
                    title = {
                        Icon(
                            painter = painterResource(Res.drawable.tmdb_logo),
                            contentDescription = ContentDescription.TMDB_LOGO,
                            modifier = Modifier
                                .graphicsLayer { alpha = 0.99f }
                                .drawWithContent {
                                    drawContent()
                                    drawRect(
                                        brush = Brush.linearGradient(
                                            colors = appLogoGradientColor
                                        ),
                                        blendMode = BlendMode.SrcAtop,
                                    )
                                }
                        )
                    },
                    actions = {
                        IconButton(
                            modifier = Modifier.size(MaterialTheme.sizing.large),
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = ContentDescription.SEARCH,
                            )
                        }
                        Spacer(modifier = Modifier.width(MaterialTheme.spacing.extraSmall))
                    },
                )
            }
        },
        bottomBar = {
            TabView(navController = navController)
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Screen.Graphs.MoviesGraph,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(400)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(400)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(400)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(400)
                )
            },
        ) {
            moviesGraph(
                paddingValues = paddingValues,
                navController = navController,
                onEvent = homeScreenModel::onEvent
            )

            tvSeriesGraph(
                paddingValues = paddingValues,
                navController = navController,
                onEvent = homeScreenModel::onEvent
            )

            peoplesGraph(
                paddingValues = paddingValues,
                navController = navController,
                onEvent = homeScreenModel::onEvent
            )

            settingsGraph(
                paddingValues = paddingValues,
                onEvent = homeScreenModel::onEvent
            )
        }
    }
}