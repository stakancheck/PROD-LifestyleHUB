/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.api.models

import kotlinx.serialization.Serializable

/**
 * DTO for error response from the API
 *
 * Raw:
 * {
 *   "meta": {
 *     "code": 401,
 *     "errorType": "invalid_auth",
 *     "errorDetail": "OAuth token invalid or revoked.",
 *     "requestId": "65fd75e1f12e584816623a04"
 *   },
 *   "response": {}
 * }
 */

@Serializable
data class VenueErrorDTO(
    val meta: Meta
) : Exception() {
    override val message: String
        get() = meta.errorDetail
}

@Serializable
data class Meta(
    val code: Int,
    val errorType: String,
    val errorDetail: String,
    val requestId: String
)
