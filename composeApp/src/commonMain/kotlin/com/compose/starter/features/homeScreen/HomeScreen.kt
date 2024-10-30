package com.compose.starter.features.homeScreen

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.compose.starter.commonUi.TabView
import com.compose.starter.constants.ContentDescription
import com.compose.starter.navGraphs.Movie
import com.compose.starter.navGraphs.Person
import com.compose.starter.navGraphs.Setting
import com.compose.starter.navGraphs.Tv
import com.compose.starter.navGraphs.moviesGraph
import com.compose.starter.navGraphs.personsGraph
import com.compose.starter.navGraphs.settingsGraph
import com.compose.starter.navGraphs.tvSeriesGraph
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing
import com.compose.starter.theme.appLogoGradientColor
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.tmdb_logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.ext.getFullName

@Composable
@Preview
fun HomeScreen() {
    val navController = rememberNavController()
    var uiState by remember { mutableStateOf(HomeScreenState()) }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(currentBackStackEntry?.destination?.route) {
        val value = startDestinations.any {
            it::class.getFullName().replace("$", ".") == currentBackStackEntry?.destination?.route
        }
        uiState = uiState.copy(
            shouldDisplayAppBar = value,
            shouldDisplayBottomBar = value,
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (uiState.shouldDisplayAppBar) {
            TmdbAppBar()
        }

        Box(modifier = Modifier.weight(1f)) {
            NavigationContent(navController)
        }

        if (uiState.shouldDisplayBottomBar) {
            TabView(navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TmdbAppBar() {
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
        }
    )
}

@Composable
private fun NavigationContent(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Movie.Graph,
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
        moviesGraph(navController = navController)

        tvSeriesGraph(navController = navController)

        personsGraph(navController = navController)

        settingsGraph()
    }
}

private val startDestinations = listOf(
    Movie.Movies,
    Tv.TvSeries,
    Person.Persons,
    Setting.Settings,
)
