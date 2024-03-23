/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.repository

import ru.stakancheck.api.WeatherApi
import ru.stakancheck.api.models.ApiResult
import ru.stakancheck.api.models.Language
import ru.stakancheck.common.Logger
import ru.stakancheck.common.error.DataError
import ru.stakancheck.common.error.toNetworkError
import ru.stakancheck.data.mappers.ApiExceptionToDataErrorMapper
import ru.stakancheck.data.mappers.CurrentWeatherDTOToWeatherMapper
import ru.stakancheck.data.models.Weather
import ru.stakancheck.data.utils.Result
import java.util.Locale


/**
 * Repository class for handling weather data.
 *
 * @property weatherApi The API interface for fetching weather data.
 * @property locationRepository The repository for handling location data.
 * @property logger A logger for logging events.
 * @property errorCollector An error collector for collecting and notifying about errors.
 */
class WeatherRepository(
    private val weatherApi: WeatherApi,
    private val locationRepository: LocationRepository,
    private val logger: Logger,
) {

    /**
     * Fetches the current weather data based on the provided latitude, longitude, and language.
     *
     * @param lat The latitude of the location.
     * @param long The longitude of the location.
     * @param lang The language for the weather data. Defaults to the device's default language.
     * @return A Resource object containing the Weather data or an error.
     */
    suspend fun getCurrentWeather(
        lang: String = Locale.getDefault().language
    ): Result<Weather, DataError.Network> {
        // Get location and collect all data
        return when (val locationResult = locationRepository.getLocation()) {
            is Result.Error -> {
                Result.Error(locationResult.error.toNetworkError())
            }

            is Result.Success -> {
                return when (val weatherResult = weatherApi.getCurrentWeather(
                    lat = locationResult.data.latitude,
                    long = locationResult.data.longitude,
                    lang = provideLocale(lang)
                )) {
                    is ApiResult.Error -> {
                        logger.e(tag = TAG, message = "getCurrentWeather: ${weatherResult.error}")
                        Result.Error(ApiExceptionToDataErrorMapper(weatherResult.error))
                    }

                    is ApiResult.Success -> {
                        val weather = CurrentWeatherDTOToWeatherMapper(weatherResult.data)
                        logger.d(tag = TAG, message = "getCurrentWeather: ${weatherResult.data}")
                        Result.Success(weather)
                    }
                }
            }
        }


    }


    companion object {
        const val TAG = "WeatherRepository"

        /**
         * Provides the Language enum value based on the provided language code.
         *
         * @param code The language code.
         * @return The Language enum value corresponding to the provided code. Defaults to Russian if the code is not recognized.
         */
        private fun provideLocale(code: String): Language {
            return Language.fromCode(code) ?: Language.RUSSIAN
        }
    }
}
