package com.compose.starter

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.compose.starter.appInitializations.AppInitialData
import com.compose.starter.appInitializations.AppInitialLoad

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        var isDataLoaded = false
        var initialData: AppInitialData? = null
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            )
        )

        splashScreen.setKeepOnScreenCondition {
            !isDataLoaded
        }

        setContent {
            AppInitialLoad.loadData {
                initialData = it
                isDataLoaded = true
            }

            App(initialData)
        }
    }
}