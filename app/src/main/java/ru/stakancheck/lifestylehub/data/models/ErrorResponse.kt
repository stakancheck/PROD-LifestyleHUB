/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.lifestylehub.data.models

import kotlinx.serialization.Serializable
import ru.stakancheck.lifestylehub.data.api.ApiResult


@Serializable
data class ErrorResponse(
    val cod: String,
    val message: String,
) : ApiResult.Companion.ErrorResponse {
    override val error: String
        get() = "Error while getting weather"
    override val errorDescription: String
        get() = message
}