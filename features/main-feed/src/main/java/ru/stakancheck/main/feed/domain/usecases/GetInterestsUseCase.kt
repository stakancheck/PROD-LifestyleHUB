/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.main.feed.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.stakancheck.common.error.ErrorCollector
import ru.stakancheck.data.models.Interest
import ru.stakancheck.data.repository.InterestRepository
import ru.stakancheck.main.feed.domain.sources.InterestsSource

class GetInterestsUseCase(
    private val interestRepository: InterestRepository,
    private val errorCollector: ErrorCollector
) {
    suspend operator fun invoke(): Flow<PagingData<Interest>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 5,
            ),
            pagingSourceFactory = {
                InterestsSource(
                    interestRepository = interestRepository,
                    errorCollector = errorCollector
                )
            }
        ).flow
    }
}