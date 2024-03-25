/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.utils

import ru.stakancheck.api.models.tools.ApiResult
import ru.stakancheck.common.error.DataError
import ru.stakancheck.data.mappers.ApiExceptionToDataErrorMapper


/**
 * Sealed class for handling success and error states
 *
 * @param D Data type
 * @param E Error type, see [ru.stakancheck.common.error.Error]
 */
sealed class CachebleResult<out D : Any, out E : RootError>(open val data: D? = null) {
    /**
     * _InProgress state_
     *
     * **Use it when:**
     * We may have already received data from the cache, but we have not updated it with data from the server, so the process is not finished, but the data has already been received.
     * Or we haven't received data from the server yet, but an error occurred while receiving or saving the cache, so we are waiting for data from the only available source.
     *
     * @param D Data type
     * @param E Error type, see [ru.stakancheck.common.error.Error]
     */
    data class InProgress<out D : Any, out E : RootError>(override val data: D? = null) :
        CachebleResult<D, E>(data)

    /**
     * _Success state_
     *
     * **Use it when:**
     * Only when we successfully received the data from the server and updated it in the cache. Users should be sure that this is new data in this state.
     *
     * @param D Data type
     * @param E Error type, see [ru.stakancheck.common.error.Error]
     */
    data class Success<out D : Any, out E : RootError>(override val data: D) :
        CachebleResult<D, E>(data)

    /**
     *  _Error state_
     *
     *  **Use it when:**
     *  We were unable to get the data from the server and update it. Only in this case should the user receive an error. Even if the data from the cache is available, we have to return an error.
     *
     *  @param D Data type
     *  @param E Error type, see [ru.stakancheck.common.error.Error]
     */
    data class Error<out D : Any, out E : RootError>(
        override val data: D? = null,
        val error: E? = null
    ) : CachebleResult<D, E>(data)
}

/**
 * Map some data in CachebleResult (use it for changing data type)
 *
 * **Like this:**
 * - DTO -> MODEL
 * - DBO -> MODEL
 */
fun <I : Any, O : Any, E : RootError> CachebleResult<I, E>.map(mapper: (I) -> O): CachebleResult<O, E> {
    return when (this) {
        is CachebleResult.Success -> CachebleResult.Success(mapper(data))
        is CachebleResult.Error -> CachebleResult.Error(data?.let(mapper))
        is CachebleResult.InProgress -> CachebleResult.InProgress(data?.let(mapper))
    }
}

/**
 * Converts any ApiResult to same representation in CachebleResult
 */
internal fun <D : Any> ApiResult<D>.toCachebleResult(): CachebleResult<D, DataError.Network> {
    return when (this) {
        is ApiResult.Error -> CachebleResult.Error(error = ApiExceptionToDataErrorMapper(this.error))
        is ApiResult.Success -> CachebleResult.Success(this.data)
    }
}
