/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.main.feed.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import ru.stakancheck.common.error.DataError
import ru.stakancheck.common.error.ErrorCollector
import ru.stakancheck.data.models.VenueDetails
import ru.stakancheck.data.repository.InterestRepository
import ru.stakancheck.data.utils.CachebleResult

class GetVenueDetailsByIdUseCase(
    private val interestRepository: InterestRepository,
    private val errorCollector: ErrorCollector
) {
    operator fun invoke(venueId: String): Flow<CachebleResult<VenueDetails, DataError>> {
        return interestRepository.getVenueDetails(venueId).onEach {
            if (it is CachebleResult.Error && it.error != null) {
                errorCollector.notifyError(it.error!!)
            }
        }
    }
}
