package com.compose.starter.commonUi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.compose.starter.navGraphs.Movie
import com.compose.starter.navGraphs.Person
import com.compose.starter.navGraphs.Setting
import com.compose.starter.navGraphs.Tv
import com.compose.starter.networking.model.TabBarItem
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.movie_filled
import composestarter.composeapp.generated.resources.movie_outlined
import composestarter.composeapp.generated.resources.movies
import composestarter.composeapp.generated.resources.people
import composestarter.composeapp.generated.resources.people_filled
import composestarter.composeapp.generated.resources.people_outline
import composestarter.composeapp.generated.resources.settings
import composestarter.composeapp.generated.resources.settings_filled
import composestarter.composeapp.generated.resources.settings_outlined
import composestarter.composeapp.generated.resources.tvSeries
import composestarter.composeapp.generated.resources.tv_filled
import composestarter.composeapp.generated.resources.tv_outlined
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun TabView(navController: NavController) {
    val tabBarItems = bottomTabs()
    val bottomNavDestinations = listOf(
        Movie.Graph,
        Tv.Graph,
        Person.Graph,
        Setting.Graph,
    )

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val entry by navController.currentBackStackEntryAsState()
        val currentDestination = entry?.destination

        bottomNavDestinations.forEachIndexed { index, destination ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.hasRoute(destination::class)
            } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(
                        tabBarItems[index].screen
                    ) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    TabBarIconView(
                        isSelected = isSelected,
                        selectedIcon = tabBarItems[index].selectedIcon,
                        unselectedIcon = tabBarItems[index].unselectedIcon,
                        title = tabBarItems[index].title,
                        badgeAmount = 0
                    )
                },
                label = {
                    Text(tabBarItems[index].title)
                }
            )
        }
    }
}

/**
 * Compose way to change navigation bar and status bar color
 *
 * @param statusBarColor color parameter for status bar
 * @param bottomNavColor color for bottom navigation bar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun statusNavModifier(
    statusBarColor: Color? = null,
    bottomNavColor: Color? = null,
): Modifier {
    return Modifier
        .background(
            Brush.verticalGradient(
                0.5f to (statusBarColor ?: TopAppBarDefaults.topAppBarColors().containerColor),
                1f to (bottomNavColor ?: MaterialTheme.colorScheme.surfaceContainer),
            )
        )
        .systemBarsPadding()
}


@Composable
private fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: DrawableResource,
    unselectedIcon: DrawableResource,
    title: String,
    badgeAmount: Int? = null,
) {
    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
        Icon(
            painter = if (isSelected) {
                painterResource(selectedIcon)
            } else {
                painterResource(unselectedIcon)
            },
            contentDescription = title
        )
    }
}

@Composable
private fun TabBarBadgeView(count: Int? = null) {
    if (count != null && count != 0) {
        Badge {
            Text(count.toString())
        }
    }
}

@Composable
private fun bottomTabs(): List<TabBarItem> {
    val moviesTab = TabBarItem(
        screen = Movie.Graph,
        title = stringResource(Res.string.movies),
        selectedIcon = Res.drawable.movie_filled,
        unselectedIcon = Res.drawable.movie_outlined
    )
    val tvSeriesTab = TabBarItem(
        screen = Tv.Graph,
        title = stringResource(Res.string.tvSeries),
        selectedIcon = Res.drawable.tv_filled,
        unselectedIcon = Res.drawable.tv_outlined
    )
    val peopleTab = TabBarItem(
        screen = Person.Graph,
        title = stringResource(Res.string.people),
        selectedIcon = Res.drawable.people_filled,
        unselectedIcon = Res.drawable.people_outline
    )
    val settingsTab = TabBarItem(
        screen = Setting.Graph,
        title = stringResource(Res.string.settings),
        selectedIcon = Res.drawable.settings_filled,
        unselectedIcon = Res.drawable.settings_outlined
    )

    return listOf(moviesTab, tvSeriesTab, peopleTab, settingsTab)
}