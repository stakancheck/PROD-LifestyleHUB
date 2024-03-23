/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.utils

import ru.stakancheck.common.error.Error

typealias RootError = Error


/**
 * Sealed class for handling success and error states
 *
 * @param D Data type
 * @param E Error type, see [ru.stakancheck.common.error.Error]
 * */
sealed interface Result<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : Result<D, E>
    data class Error<out D, out E : RootError>(val error: E) : Result<D, E>
}
