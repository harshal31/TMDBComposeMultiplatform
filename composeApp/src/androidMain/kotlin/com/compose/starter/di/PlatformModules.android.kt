package com.compose.starter.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModules(context: Any?, isLogEnabled: Boolean): Module {
    require(context is Context) {
        "Context is Needed"
    }
    if (isLogEnabled) {
        Napier.base(DebugAntilog())
    }
    return module {
        factory<HttpClientEngine> { OkHttp.create() }
        factory<DataStore<Preferences>> {
            createDataStore(
                producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
            )
        }
    }
}