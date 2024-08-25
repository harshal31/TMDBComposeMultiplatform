package com.compose.starter

import androidx.compose.ui.window.ComposeUIViewController
import com.compose.starter.di.appLevelModules
import org.koin.compose.KoinApplication
import org.koin.compose.KoinContext
import org.koin.dsl.koinApplication

fun MainViewController() = ComposeUIViewController {
    KoinContext(
        context = koinApplication {
            appLevelModules(null, true)
        }.koin
    ) {
        App()
    }
}