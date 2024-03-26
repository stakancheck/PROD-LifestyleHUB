/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.leisure.edit.domain.usecases

import ru.stakancheck.common.error.DataError
import ru.stakancheck.common.error.ErrorCollector
import ru.stakancheck.data.models.LeisureEntry
import ru.stakancheck.data.repository.LeisureEntryRepository
import ru.stakancheck.data.utils.Result

class GetLeisureEntryUseCase(
    private val leisureEntryRepository: LeisureEntryRepository,
    private val errorCollector: ErrorCollector,
) {
    suspend operator fun invoke(leisureId: Long): Result<LeisureEntry, DataError.Local> {
        val result = leisureEntryRepository.getLeisureEntry(leisureId)

        if (result is Result.Error && result.error != DataError.Local.NO_DATA) {
            errorCollector.notifyError(result.error)
        }

        return result
    }
}
