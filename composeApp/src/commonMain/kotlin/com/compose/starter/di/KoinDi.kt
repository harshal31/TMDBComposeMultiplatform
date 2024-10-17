package com.compose.starter.di

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.disk.DiskCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.compose.starter.appInitializations.AppBuildInfo
import com.compose.starter.appInitializations.AppLevelViewModel
import com.compose.starter.features.loginScreen.LoginScreenRepository
import com.compose.starter.features.loginScreen.LoginScreenViewModel
import com.compose.starter.features.movieDetailScreen.MovieDetailScreenModel
import com.compose.starter.features.movieDetailScreen.MovieDetailScreenRepository
import com.compose.starter.features.movieDetailScreen.MovieDetailScreenUseCase
import com.compose.starter.features.moviesScreen.MoviesScreenRepository
import com.compose.starter.features.moviesScreen.MoviesScreenViewModel
import com.compose.starter.features.peopleScreen.PeopleScreenRepository
import com.compose.starter.features.peopleScreen.PeopleScreenViewModel
import com.compose.starter.features.settingsScreen.SettingsScreenRepository
import com.compose.starter.features.settingsScreen.SettingsScreenViewModel
import com.compose.starter.features.tvSeriesScreen.TvSeriesScreenRepository
import com.compose.starter.features.tvSeriesScreen.TvSeriesScreenViewModel
import com.compose.starter.localData.LocalStore
import com.compose.starter.networking.NetworkManager
import okio.FileSystem
import org.koin.core.KoinApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.mp.KoinPlatformTools


fun appLevelBuildInfo(
    isLogEnabled: Boolean,
) = module {
    single<AppBuildInfo> {
        AppBuildInfo(
            isLoggingEnabled = isLogEnabled,
        )
    }
}

private val repoConstructorParamModules = module {
    single<NetworkManager> { NetworkManager(httpClientEngine = get(), buildInfo = get()) }
    single<LocalStore> { LocalStore(store = get()) }
    single<ShareMediaData> { ShareMediaData() }
}

private val repoModules = module {
    factory<LoginScreenRepository> { LoginScreenRepository(network = get(), store = get()) }
    factory<MoviesScreenRepository> { MoviesScreenRepository(network = get()) }
    factory<TvSeriesScreenRepository> { TvSeriesScreenRepository(network = get()) }
    factory<SettingsScreenRepository> { SettingsScreenRepository(network = get(), store = get()) }
    factory<PeopleScreenRepository> { PeopleScreenRepository(network = get()) }
    factory<MovieDetailScreenRepository> {
        MovieDetailScreenRepository(
            network = get(),
            store = get()
        )
    }
}

private val useCaseModules = module {
    factory {
        MovieDetailScreenUseCase(
            movieDetailRepo = get(),
            settingRepo = get(),
            shareMediaData = get()
        )
    }
}

private val viewModelModules = module {
    viewModel<LoginScreenViewModel> { LoginScreenViewModel(repository = get()) }
    viewModel<MoviesScreenViewModel> { MoviesScreenViewModel(repository = get()) }
    viewModel<TvSeriesScreenViewModel> { TvSeriesScreenViewModel(repository = get()) }
    viewModel<SettingsScreenViewModel> { SettingsScreenViewModel(repository = get()) }
    viewModel<AppLevelViewModel> { AppLevelViewModel(store = get()) }
    viewModel<PeopleScreenViewModel> { PeopleScreenViewModel(repository = get()) }
    viewModel<MovieDetailScreenModel> {
        MovieDetailScreenModel(
            movieDetailUseCase = get(),
            repository = get(),
        )
    }
}


fun KoinApplication.appLevelModules(
    context: Any?,
    isLoggingEnabled: Boolean,
): KoinApplication {
    return modules(
        platformModules(
            context, isLoggingEnabled
        ) + appLevelBuildInfo(isLoggingEnabled) + appModules
    )
}

val appModules = listOf(
    repoConstructorParamModules, repoModules, useCaseModules, viewModelModules
)


inline fun <reified T : Any> getKoinValue() = KoinPlatformTools.defaultContext().get().get<T>()


fun imageLoader(context: PlatformContext, shouldEnableLogs: Boolean = true): ImageLoader {
    return ImageLoader.Builder(context).crossfade(true)
        .logger(if (shouldEnableLogs) DebugLogger() else null)
        .diskCache {
            DiskCache.Builder()
                .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
                .maxSizeBytes(512L * 1024 * 1024)
                .build()
        }
        .diskCachePolicy(CachePolicy.ENABLED)
        .build()
}
