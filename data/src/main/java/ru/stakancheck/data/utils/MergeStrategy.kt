/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.utils


/**
 * Merge strategy for two values interface
 */
interface MergeStrategy<E> {
    fun merge(
        right: E,
        left: E
    ): E
}

/**
 * Default implementation for MergeStrategy
 *
 * Allows you to determine which response to return at different stages of receiving data from different sources.
 * There are 9 merge strategies in total.
 */
internal class RequestResponseMergeStrategy<D : Any, E : RootError> :
    MergeStrategy<CachebleResult<D, E>> {
    @Suppress("CyclomaticComplexMethod")
    override fun merge(
        right: CachebleResult<D, E>,
        left: CachebleResult<D, E>
    ): CachebleResult<D, E> {
        return when {
            right is CachebleResult.InProgress && left is CachebleResult.InProgress -> merge(
                right,
                left
            )

            right is CachebleResult.Success && left is CachebleResult.InProgress -> merge(
                right,
                left
            )

            right is CachebleResult.InProgress && left is CachebleResult.Success -> merge(
                right,
                left
            )

            right is CachebleResult.Success && left is CachebleResult.Success -> merge(right, left)
            right is CachebleResult.Success && left is CachebleResult.Error -> merge(right, left)
            right is CachebleResult.InProgress && left is CachebleResult.Error -> merge(right, left)
            right is CachebleResult.Error && left is CachebleResult.InProgress -> merge(left)
            right is CachebleResult.Error && left is CachebleResult.Success -> merge(left)
            else -> CachebleResult.Error()
        }
    }

    /**
     * If both responses are in progress, we return the one that has data with progress state
     */
    private fun merge(
        cache: CachebleResult.InProgress<D, E>,
        server: CachebleResult.InProgress<D, E>
    ): CachebleResult<D, E> {
        return when {
            server.data != null -> CachebleResult.InProgress(server.data)
            else -> CachebleResult.InProgress(cache.data)
        }
    }

    /**
     * If the cache is in progress and the server is successful, we return the server response with progress state
     */
    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: CachebleResult.Success<D, E>,
        server: CachebleResult.InProgress<D, E>
    ): CachebleResult<D, E> {
        return CachebleResult.InProgress(cache.data)
    }

    /**
     * If the cache is successful and the server is successful, we return the server response with progress state
     */
    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: CachebleResult.InProgress<D, E>,
        server: CachebleResult.Success<D, E>
    ): CachebleResult<D, E> {
        return CachebleResult.InProgress(server.data)
    }

    /**
     * If the cache is successful and the server is an error, we return the cached data with server error
     */
    private fun merge(
        cache: CachebleResult.Success<D, E>,
        server: CachebleResult.Error<D, E>
    ): CachebleResult<D, E> {
        return CachebleResult.Error(data = cache.data, error = server.error)
    }

    /**
     * If both responses are success, we return the server response with success state
     */
    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: CachebleResult.Success<D, E>,
        server: CachebleResult.Success<D, E>
    ): CachebleResult<D, E> {
        return CachebleResult.Success(data = server.data)
    }

    /**
     * If the cache is in progress and the server is an error, we return the one that has data with server error
     */
    private fun merge(
        cache: CachebleResult.InProgress<D, E>,
        server: CachebleResult.Error<D, E>
    ): CachebleResult<D, E> {
        return CachebleResult.Error(data = server.data ?: cache.data, error = server.error)
    }

    /**
     * If the cache is an error and the server is in progress, we return the server response with progress state
     */
    private fun merge(
        server: CachebleResult.InProgress<D, E>
    ): CachebleResult<D, E> {
        return server
    }

    /**
     * If the cache is an error and the server is successful, we return the server response with success state
     */
    private fun merge(
        server: CachebleResult.Success<D, E>
    ): CachebleResult<D, E> {
        return server
    }
}