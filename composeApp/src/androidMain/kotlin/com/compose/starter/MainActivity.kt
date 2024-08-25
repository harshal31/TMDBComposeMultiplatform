package com.compose.starter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.compose.starter.di.appLevelModules
import org.koin.compose.KoinApplication
import org.koin.compose.KoinContext
import org.koin.dsl.koinApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinContext(
                context = koinApplication {
                    appLevelModules(this@MainActivity.applicationContext, true)
                }.koin
            ) {
                App()
            }
        }
    }
}