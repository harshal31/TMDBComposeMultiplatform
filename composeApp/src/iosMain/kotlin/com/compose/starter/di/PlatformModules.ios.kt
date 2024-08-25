package com.compose.starter.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.core.module.factory
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual fun platformModules(context: Any?, isLogEnabled: Boolean): Module {
    if (isLogEnabled) {
        Napier.base(DebugAntilog())
    }
    return module {
        factory<HttpClientEngine> { Darwin.create() }
        factory<DataStore<Preferences>> {
            createDataStore(
                producePath = {
                    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                        directory = NSDocumentDirectory,
                        inDomain = NSUserDomainMask,
                        appropriateForURL = null,
                        create = false,
                        error = null,
                    )
                    (documentDirectory?.path ?: "") + "/$dataStoreFileName"
                }
            )
        }
    }
}