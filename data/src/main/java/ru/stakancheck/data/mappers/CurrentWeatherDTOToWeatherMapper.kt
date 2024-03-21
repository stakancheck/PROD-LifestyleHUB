/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.mappers

import ru.stakancheck.api.models.CurrentWeatherDTO
import ru.stakancheck.data.models.Weather

class CurrentWeatherDTOToWeatherMapper {
    companion object {
        operator fun invoke(
            dto: CurrentWeatherDTO
        ): Weather {
            return Weather(
                weatherId = dto.weather.first().id,
                weatherGroup = dto.weather.first().main,
                description = dto.weather.first().description,
                icon = dto.weather.first().icon,
                temp = dto.main.temp,
                feelsLike = dto.main.feelsLike,
                pressure = dto.main.pressure,
                humidity = dto.main.humidity,
                tempMin = dto.main.tempMin,
                tempMax = dto.main.tempMax,
                windSpeed = dto.wind.speed,
                windDeg = dto.wind.deg,
                dt = dto.dt,
                sunrise = dto.sys.sunrise,
                sunset = dto.sys.sunset,
                location = dto.name
            )
        }
    }
}
