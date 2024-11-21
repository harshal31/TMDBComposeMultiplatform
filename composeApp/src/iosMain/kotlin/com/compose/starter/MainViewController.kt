package com.compose.starter

import androidx.compose.ui.window.ComposeUIViewController
import com.compose.starter.appInitializations.AppInitialData
import com.compose.starter.appInitializations.AppInitialLoad
import com.compose.starter.di.initializeModules
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    startKoin {
        initializeModules(null, true)
    }
    return ComposeUIViewController {
        var appInitialData: AppInitialData? = null

        AppInitialLoad.loadData {
            appInitialData = it
        }

        App(appInitialData)
    }
}
