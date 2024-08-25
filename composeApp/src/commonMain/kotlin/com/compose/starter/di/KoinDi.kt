package com.compose.starter.di

import com.compose.starter.appLevelBuildInfo.AppBuildInfo
import com.compose.starter.features.homeScreen.HomeScreenRepository
import com.compose.starter.features.homeScreen.HomeScreenViewModel
import com.compose.starter.features.loginScreen.LoginScreenRepository
import com.compose.starter.features.loginScreen.LoginScreenViewModel
import com.compose.starter.localData.LocalStore
import com.compose.starter.networking.NetworkManager
import org.koin.core.KoinApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.mp.KoinPlatform


private fun appLevelBuildInfo(isLogEnabled: Boolean) = module {
    single<AppBuildInfo> {
        AppBuildInfo(isLoggingEnabled = isLogEnabled)
    }
}

private val repoConstructorParamModules = module {
    single<NetworkManager> { NetworkManager(get(), get()) }
    single<LocalStore> { LocalStore(get()) }
}

private val repoModules = module {
    factory<LoginScreenRepository> { LoginScreenRepository(get(), get()) }
    factory<HomeScreenRepository> { HomeScreenRepository(get(), get()) }
}

private val viewModelModules = module {
    viewModel<LoginScreenViewModel> { LoginScreenViewModel(get()) }
    viewModel<HomeScreenViewModel> { HomeScreenViewModel(get()) }
}


fun KoinApplication.appLevelModules(context: Any?, isLoggingEnabled: Boolean): KoinApplication {
    return modules(
        platformModules(
            context,
            isLoggingEnabled
        ) + appLevelBuildInfo(isLoggingEnabled) + appModules
    )
}

val appModules = listOf(
    repoConstructorParamModules, repoModules, viewModelModules
)


inline fun <reified T : Any> getValue() = KoinPlatform.getKoin().get<T>()

