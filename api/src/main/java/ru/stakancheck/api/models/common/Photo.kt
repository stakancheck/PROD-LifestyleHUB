/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.api.models.common

import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val id: String,
    val prefix: String,
    val suffix: String,
)