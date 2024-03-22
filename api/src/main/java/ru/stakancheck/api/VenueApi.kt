/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLProtocol
import io.ktor.http.headers
import io.ktor.http.path
import ru.stakancheck.api.models.ApiResult
import ru.stakancheck.api.models.Language
import ru.stakancheck.api.models.VenueErrorDTO
import ru.stakancheck.api.models.VenuesDTO


/**
 * This class is responsible for making API calls to the Venue API.
 *
 * @property httpClient The HTTP client used to make the API calls.
 * @property oauthToken The OAuth token used for authentication.
 */
class VenueApi(
    private val httpClient: HttpClient,
    private val oauthToken: String,
) {
    /**
     * This function retrieves the nearest venues for a given location.
     * (APi details)[https://location.foursquare.com/developer/reference/get-venue-recommendations]
     *
     * @param lat The latitude of the location.
     * @param long The longitude of the location.
     * @param lang The language in which the response should be returned.
     * @param limit The maximum number of results to return.
     * @param offset The number of results to skip before starting to return results.
     * @return An ApiResult object containing the current weather data or an error.
     */
    suspend fun getNearVenues(
        lat: Double,
        long: Double,
        lang: Language,
        limit: Int,
        offset: Int,
    ): ApiResult<VenuesDTO> =
        ApiResult.withCatching<VenuesDTO, VenueErrorDTO> {
            val httpResponse: HttpResponse = httpClient.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = HOST
                    path("v2", "search", "recommendations")

                    parameters.append("ll", formatLocationParameter(lat, long))
                    parameters.append("oauth_token", oauthToken)
                    parameters.append("locale", lang.code)
                    parameters.append("limit", limit.toString())
                    parameters.append("offset", offset.toString())
                    parameters.append("v", API_VERSION)
                }

                headers {
                    append("Content-Type", "application/json")
                    append("Accept", "application/json")
                }
            }
            httpResponse.body()
        }

    companion object {
        const val HOST = "api.foursquare.com"
        const val API_VERSION = "20230321"

        /**
         * This function formats the latitude and longitude into a string that can be used as a parameter in the API call.
         *
         * @param lat The latitude.
         * @param long The longitude.
         * @return A string containing the formatted latitude and longitude.
         */
        private fun formatLocationParameter(lat: Double, long: Double): String {
            return "$lat,$long"
        }
    }
}
