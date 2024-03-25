/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.utils

import ru.stakancheck.common.error.Error


/**
 * Sealed class for handling success and error states
 *
 * @param D Data type
 * @param E Error type, see [ru.stakancheck.common.error.Error]
 * */
sealed interface Result<out D : Any, out E : RootError> {
    data class Success<out D : Any, out E : RootError>(val data: D) : Result<D, E>
    data class Error<out D : Any, out E : RootError>(val error: E) : Result<D, E>

    companion object {
        fun <D : Any, E : RootError> success(data: D): Result<D, E> = Success(data)

        fun <D : Any, E : RootError> error(error: E): Result<D, E> = Error(error)

        val <D : Any, E : RootError> Result<D, E>.isSuccess: Boolean
            get() = this is Success

        val <D : Any, E : RootError> Result<D, E>.isError: Boolean
            get() = this is Error

        fun <D : Any, E : RootError> Result<D, E>.getOrThrow(): D {
            return when (this) {
                is Success -> data
                is Error -> kotlin.error(this.error)
            }
        }
    }
}
