/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.main.feed.domain.usecases

import ru.stakancheck.common.error.DataError
import ru.stakancheck.common.error.ErrorCollector
import ru.stakancheck.data.repository.WeatherRepository
import ru.stakancheck.data.utils.Result
import ru.stakancheck.data.utils.map
import ru.stakancheck.main.feed.entities.WeatherUIModel
import ru.stakancheck.main.feed.mappers.WeatherResultToUIModelMapper

class GetCurrentWeatherUseCase(
    private val weatherRepository: WeatherRepository,
    private val errorCollector: ErrorCollector,
) {
    suspend operator fun invoke(): Result<WeatherUIModel, DataError> {
        val result = weatherRepository.getCurrentWeather()
        if (result is Result.Error) {
            errorCollector.notifyError(result.error)
        }
        return result.map { WeatherResultToUIModelMapper(it) }
    }
}
