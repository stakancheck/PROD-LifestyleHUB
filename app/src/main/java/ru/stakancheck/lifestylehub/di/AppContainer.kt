/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.lifestylehub.di

import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.stakancheck.api.VenueApi
import ru.stakancheck.api.WeatherApi
import ru.stakancheck.common.AndroidLogger
import ru.stakancheck.common.Logger
import ru.stakancheck.common.error.ErrorCollector
import ru.stakancheck.common.error.ErrorObserver
import ru.stakancheck.common.permission.PermissionChecker
import ru.stakancheck.common.permission.PermissionCheckerImpl
import ru.stakancheck.data.repository.InterestRepository
import ru.stakancheck.data.repository.LeisureEntryRepository
import ru.stakancheck.data.repository.LocationRepository
import ru.stakancheck.data.repository.WeatherRepository
import ru.stakancheck.database.AppDatabase
import ru.stakancheck.leisure.list.domain.usecases.GetLeisureListUseCase
import ru.stakancheck.leisure.list.presentation.LeisureListScreenViewModel
import ru.stakancheck.lifestylehub.BuildConfig
import ru.stakancheck.lifestylehub.utils.LoggerBridgeImpl
import ru.stakancheck.main.feed.domain.usecases.GetCurrentWeatherUseCase
import ru.stakancheck.main.feed.domain.usecases.GetInterestsUseCase
import ru.stakancheck.main.feed.presentation.MainFeedScreenViewModel
import ru.stakancheck.uikit.components.ErrorPresenter
import ru.stakancheck.uikit.components.ToastErrorPresenter
import ru.stakancheck.venue.details.domain.usecases.GetVenueDetailsByIdUseCase
import ru.stakancheck.venue.details.presentation.VenueDetailsScreenViewModel
import java.util.concurrent.CopyOnWriteArrayList
import ru.stakancheck.api.HttpClient as AppHttpClient

private val main = module {

    // Error collector (only single instance)
    single<ErrorCollector> {
        object : ErrorCollector {
            override val observers: CopyOnWriteArrayList<ErrorObserver>
                get() = CopyOnWriteArrayList<ErrorObserver>().apply {
                    add(get<ErrorPresenter>())
                }
        }
    }

    // Error presenter with composable invoke
    single<ErrorPresenter> {
        ToastErrorPresenter()
    }

    // Logger (only single instance)
    single<Logger> {
        AndroidLogger()
    }

}

private val data = module {
    single<HttpClient> {
        AppHttpClient(
            loggerBridge = LoggerBridgeImpl(logger = get())
        )
    }

    single<WeatherApi> {
        WeatherApi(
            httpClient = get(),
            apiKey = BuildConfig.OPEN_WEATHER_API_KEY
        )
    }

    single<VenueApi> {
        VenueApi(
            httpClient = get(),
            oauthToken = BuildConfig.FORSQUARE_API_TOKEN,
        )
    }

    single<AppDatabase> {
        AppDatabase(androidContext())
    }

    single<WeatherRepository> {
        WeatherRepository(
            weatherApi = get(),
            locationRepository = get(),
            logger = get()
        )
    }

    single<InterestRepository> {
        InterestRepository(
            venueApi = get(),
            database = get(),
            locationRepository = get(),
            logger = get()
        )
    }

    single {
        PermissionCheckerImpl(
            context = androidContext()
        )
    } bind PermissionChecker::class

    single<LocationRepository> {
        LocationRepository(
            context = androidContext(),
            permissionChecker = get(),
            logger = get()
        )
    }

    single<LeisureEntryRepository> {
        LeisureEntryRepository(
            database = get(),
            logger = get()
        )
    }
}

private val useCase = module {

    factory<GetInterestsUseCase> {
        GetInterestsUseCase(
            interestRepository = get(),
            errorCollector = get()
        )
    }

    factory<GetCurrentWeatherUseCase> {
        GetCurrentWeatherUseCase(
            weatherRepository = get(),
            errorCollector = get()
        )
    }

    factory<GetVenueDetailsByIdUseCase> {
        GetVenueDetailsByIdUseCase(
            interestRepository = get(),
            errorCollector = get()
        )
    }

    factory<GetLeisureListUseCase> {
        GetLeisureListUseCase(
            leisureEntryRepository = get(),
            errorCollector = get()
        )
    }
}

private val viewmodel = module {
    viewModel { MainFeedScreenViewModel(get(), get(), get()) }
    viewModel { VenueDetailsScreenViewModel(get(), get()) }
    viewModel { LeisureListScreenViewModel(get(), get()) }
}

val commonModule = listOf(main, data, useCase, viewmodel)
