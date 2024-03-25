/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import ru.stakancheck.api.models.tools.ApiResult
import ru.stakancheck.common.Logger
import ru.stakancheck.common.error.DataError


/**
 * CachableDataProvider is a utility class that provides a way to fetch data from a remote source and cache it locally.
 * It uses a merge strategy to combine the results from the remote and local sources.
 * It also provides a way to observe the local data and update it when the remote data changes.
 *
 * @param R The type of the remote data.
 * @param L The type of the local data.
 * @param D The type of the data after mapping.
 *
 * @param mergeStrategy The strategy to merge the results from the remote and local sources.
 * @param cachePolicy The caching policy.
 * @param saveToDatabase A function to save the data to the database.
 * @param remoteMapper A function to map the remote data to the data type.
 * @param localMapper A function to map the local data to the data type.
 * @param remoteToLocalMapper A function to map the remote data to the local data type.
 * @param logger A logger to log exceptions.
 */
internal class CachableDataProvider<R : Any, L : Any, D : Any>(
    private val mergeStrategy: MergeStrategy<CachebleResult<D, DataError>> = RequestResponseMergeStrategy(),
    private val cachePolicy: CachePolicy = CachePolicy.ALWAYS,
    private val saveToDatabase: suspend (L) -> Unit,
    private val remoteMapper: (R) -> D,
    private val localMapper: (L) -> D,
    private val remoteToLocalMapper: (R) -> L,
    private val logger: Logger,
) {

    /**
     * Fetches data from the remote source and caches it locally.
     * It then observes the local data and updates it when the remote data changes.
     *
     * @param remoteDataProvider A function to fetch data from the remote source.
     * @param localDataProvider A function to fetch data from the local source.
     * @param localDataObserveProvider A function to observe the local data.
     *
     * @return A flow of [CachebleResult] containing the data or an error.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getDataWithCaching(
        remoteDataProvider: suspend () -> ApiResult<R>,
        localDataProvider: suspend () -> L,
        localDataObserveProvider: suspend () -> Flow<L>,
    ): Flow<CachebleResult<D, DataError>> {
        val cached: Flow<CachebleResult<D, DataError.Local>> = gelAllFromDatabase(localDataProvider)
        val remote: Flow<CachebleResult<D, DataError.Network>> =
            getAllFromServer(remoteDataProvider)

        return cached.combine(remote, mergeStrategy::merge)
            .flatMapLatest { result: CachebleResult<D, DataError> ->
                if (result is CachebleResult.Success) {
                    localDataObserveProvider()
                        .map { dbo -> localMapper(dbo) }
                        .map { CachebleResult.Success(it) }
                } else {
                    flowOf(result)
                }
            }
    }

    /**
     * Fetches all data from the remote source
     *
     * @return A flow of [CachebleResult] containing the data or an error [DataError.Network].
     */
    private fun getAllFromServer(
        remoteDataProvider: suspend () -> ApiResult<R>,
    ): Flow<CachebleResult<D, DataError.Network>> {
        // Fetch data from the remote source
        val apiRequest: Flow<CachebleResult<R, DataError.Network>> = remoteDataProvider.asFlow()
            // If result is success save it to database
            .onEach { result: ApiResult<R> ->
                if (result is ApiResult.Success && cachePolicy == CachePolicy.ALWAYS) {
                    saveToDatabase(remoteToLocalMapper(result.data))
                }
            }
            // Log exceptions
            .onEach { result: ApiResult<R> ->
                if (result is ApiResult.Error) {
                    logger.e(tag = TAG, "getAllFromServer: ${result.error}")
                }
            }
            // Map the result to a CachebleResult
            .map { result: ApiResult<R> ->
                result.toCachebleResult<R>()
            }

        // Start with an in progress state (default value)
        val start = flowOf<CachebleResult<R, DataError.Network>>(CachebleResult.InProgress())

        // Merge the start and api request
        return merge(apiRequest, start)
            .map { result: CachebleResult<R, DataError.Network> ->
                result.map { remoteMapper(it) }
            }
    }

    /**
     * Fetches all data from the local source
     *
     * @return A flow of [CachebleResult] containing the data or an error [DataError.Local].
     */
    private fun gelAllFromDatabase(
        localDataProvider: suspend () -> L,
    ): Flow<CachebleResult<D, DataError.Local>> {
        // Fetch data from the local source
        val databaseRequest: Flow<CachebleResult<L, DataError.Local>> = localDataProvider.asFlow()
            // Map the data to a CachebleResult
            .map<L, CachebleResult<L, DataError.Local>> {
                CachebleResult.Success<L, DataError.Local>(data = it)
            }
            // Catch any errors and map them to a CachebleResult
            .catch {
                logger.e(tag = TAG, "gelAllFromDatabase: $it")
                emit(CachebleResult.Error(error = DataError.Local.READ_ERROR))
            }

        // Start with an in progress state (default value)
        val start = flowOf<CachebleResult<L, DataError.Local>>(CachebleResult.InProgress())

        // Merge the start and database request
        return merge(start, databaseRequest).map { result ->
            result.map { result -> localMapper(result) }
        }
    }

    private companion object {
        const val TAG = "CachableDataProvider"
    }
}

/**
 * Chaching policy variants
 */
enum class CachePolicy {
    /**
     * Never create a cache line for the key
     */
    NEVER,

    /**
     * Always create a cache line for the key
     */
    ALWAYS,
}
