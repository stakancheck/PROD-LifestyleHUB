/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.main.feed.domain.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.stakancheck.common.error.ErrorCollector
import ru.stakancheck.data.models.Interest
import ru.stakancheck.data.repository.InterestRepository
import ru.stakancheck.data.utils.Result

class InterestsSource(
    private val interestRepository: InterestRepository,
    private val errorCollector: ErrorCollector
) : PagingSource<Int, Interest>() {
    override fun getRefreshKey(state: PagingState<Int, Interest>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Interest> {
        val currentPage = params.key ?: 0

        return when (val result = interestRepository.getNearInterests(
            lat = 61.691891,
            long = 50.807930,
            page = currentPage
        )) {
            is Result.Error -> {
                errorCollector.notifyError(result.error)
                LoadResult.Error(Throwable(result.error.name))
            }

            is Result.Success -> {
                LoadResult.Page(
                    data = result.data,
                    prevKey = if (currentPage == 0) null else currentPage - 1,
                    nextKey = if (result.data.isEmpty()) null else currentPage + 1
                )
            }
        }
    }

}