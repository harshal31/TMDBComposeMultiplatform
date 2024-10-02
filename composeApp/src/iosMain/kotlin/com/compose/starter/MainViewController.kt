package com.compose.starter

import androidx.compose.ui.window.ComposeUIViewController
import com.compose.starter.appInitializations.AppInitialData
import com.compose.starter.appInitializations.AppInitialLoad
import com.compose.starter.di.appLevelModules
import org.koin.compose.KoinApplication

fun MainViewController() = ComposeUIViewController {
    var appInitialData: AppInitialData? = null
    KoinApplication({
        appLevelModules(null, true)
    }) {
        AppInitialLoad.loadData {
            appInitialData = it
        }

        App(appInitialData)
    }
}
