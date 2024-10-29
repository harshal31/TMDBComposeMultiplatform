package com.compose.starter

import android.app.Application
import android.content.Context
import com.compose.starter.di.initializeModules
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            initializeModules(get<Context>(), true)
        }
    }
}