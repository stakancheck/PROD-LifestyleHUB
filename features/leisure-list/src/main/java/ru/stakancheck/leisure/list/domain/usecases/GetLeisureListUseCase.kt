/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.leisure.list.domain.usecases

import ru.stakancheck.common.error.DataError
import ru.stakancheck.common.error.ErrorCollector
import ru.stakancheck.data.repository.LeisureEntryRepository
import ru.stakancheck.data.utils.Result
import ru.stakancheck.data.utils.map
import ru.stakancheck.leisure.list.entities.LeisureEntryUIModel
import ru.stakancheck.leisure.list.mappers.LeisureEntryToUIModelMapper

class GetLeisureListUseCase(
    private val leisureEntryRepository: LeisureEntryRepository,
    private val errorCollector: ErrorCollector,
) {
    suspend operator fun invoke(): Result<List<LeisureEntryUIModel>, DataError.Local> {
        val result = leisureEntryRepository.getLeisureEntries()
        if (result is Result.Error) {
            errorCollector.notifyError(result.error)
        }
        return result.map {
            it.map { LeisureEntryToUIModelMapper(it) }
        }
    }
}
