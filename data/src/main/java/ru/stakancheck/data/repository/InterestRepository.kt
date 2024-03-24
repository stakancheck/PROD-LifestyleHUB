/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.repository

import ru.stakancheck.api.VenueApi
import ru.stakancheck.api.models.tools.ApiResult
import ru.stakancheck.api.models.tools.Language
import ru.stakancheck.common.Logger
import ru.stakancheck.common.error.DataError
import ru.stakancheck.common.error.toNetworkError
import ru.stakancheck.data.mappers.ApiExceptionToDataErrorMapper
import ru.stakancheck.data.mappers.VenuesDTOToInterestsMapper
import ru.stakancheck.data.models.Interests
import ru.stakancheck.data.utils.Result
import java.util.Locale


/**
 * Repository class for handling interest data.
 *
 * @property venueApi The API interface for fetching interest data.
 * @property locationRepository The repository for handling location data.
 * @property logger A logger for logging events.
 */
class InterestRepository(
    private val venueApi: VenueApi,
    private val locationRepository: LocationRepository,
    private val logger: Logger,
) {

    /**
     * Fetches list of 10 venues based on the provided latitude, longitude.
     * Paging by page value (starts from 0).
     *
     * @param lang The language for the venues data localization. Defaults to the device's default language.
     * @return A Resource object containing the venues data or an error.
     */
    suspend fun getNearInterests(
        page: Int,
        lang: String = Locale.getDefault().language
    ): Result<Interests, DataError.Network> {
        // Get location and collect all data
        return when (val locationResult = locationRepository.getLocation()) {
            is Result.Error -> {
                Result.Error(locationResult.error.toNetworkError())
            }

            is Result.Success -> {
                getNearVenues(
                    lat = locationResult.data.latitude,
                    long = locationResult.data.longitude,
                    page = page,
                    lang = lang
                )
            }
        }
    }

    /**
     * Fetches list of 10 venues based on the provided latitude, longitude.
     * Paging by page value (starts from 0).
     *
     * @param lat The latitude of the location.
     * @param long The longitude of the location.
     * @param lang The language for the venues data localization. Defaults to the device's default language.
     * @return A Resource object containing the venues data or an error.
     */
    private suspend fun getNearVenues(
        lat: Double,
        long: Double,
        page: Int,
        lang: String = Locale.getDefault().language
    ): Result<Interests, DataError.Network> {
        val result = venueApi.getNearVenues(
            lat = lat,
            long = long,
            limit = PAGE_LIMIT,
            offset = provideOffset(page),
            lang = provideLocale(lang)
        )

        return when (result) {
            is ApiResult.Error -> {
                logger.e(tag = TAG, message = "getCurrentWeather: ${result.error}")
                Result.Error(ApiExceptionToDataErrorMapper(result.error))
            }

            is ApiResult.Success -> {
                val venues = VenuesDTOToInterestsMapper(result.data)
                logger.d(tag = TAG, message = "getCurrentWeather: ${result.data}")
                Result.Success(venues)
            }
        }
    }

    companion object {
        const val TAG = "VenueRepository"

        /**
         * The limit of venues to fetch per page.
         */
        const val PAGE_LIMIT = 10

        /**
         * Provides the Language enum value based on the provided language code.
         *
         * @param code The language code.
         * @return The Language enum value corresponding to the provided code. Defaults to Russian if the code is not recognized.
         */
        private fun provideLocale(code: String): Language {
            return Language.fromCode(code) ?: Language.RUSSIAN
        }

        private fun provideOffset(page: Int): Int {
            return PAGE_LIMIT * page
        }
    }
}