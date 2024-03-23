/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.stakancheck.api.WeatherApi
import ru.stakancheck.api.models.ApiResult
import ru.stakancheck.api.models.Coord
import ru.stakancheck.api.models.CurrentWeatherDTO
import ru.stakancheck.api.models.Main
import ru.stakancheck.api.models.Sys
import ru.stakancheck.api.models.Wind
import ru.stakancheck.common.Logger
import ru.stakancheck.common.error.DataError
import ru.stakancheck.data.models.Location
import ru.stakancheck.data.models.Weather
import ru.stakancheck.data.repository.LocationRepository
import ru.stakancheck.data.repository.WeatherRepository
import ru.stakancheck.data.utils.Result
import java.util.Date
import ru.stakancheck.api.models.Weather as ApiWeather

class WeatherRepositoryTest {
    private lateinit var weatherApi: WeatherApi
    private lateinit var locationRepository: LocationRepository
    private lateinit var logger: Logger
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup() {
        weatherApi = mockk()
        locationRepository = mockk()
        logger = LoggerTestImpl()
        weatherRepository = WeatherRepository(weatherApi, locationRepository, logger)
    }

    @Test
    fun `test getCurrentWeather success`() = runBlocking {
        val expectedWeather = Result.Success<Weather, DataError>(
            Weather(
                weatherId = 800,
                weatherGroup = "Clear",
                description = "Clear sky",
                icon = "01d",
                temp = 20.0,
                feelsLike = 19.0,
                pressure = 1000,
                humidity = 50,
                tempMin = 20.0,
                tempMax = 20.0,
                windSpeed = 5,
                windDeg = 180,
                dt = Date(),
                sunrise = Date(),
                sunset = Date(),
                location = "Chelyabinsk"
            )
        )

        coEvery { locationRepository.getLocation() } returns Result.Success(Location(55.0, 61.4))
        coEvery { weatherApi.getCurrentWeather(any(), any(), any()) } returns ApiResult.Success(
            CurrentWeatherDTO(
                coord = Coord(55.0, 61.4),
                weather = listOf(
                    ApiWeather(800, "Clear", "Clear sky", "01d")
                ),
                base = "stations",
                main = Main(20.0, 19.0, 1000, 50, 20.0, 20.0),
                wind = Wind(5, 180),
                dt = Date(),
                sys = Sys(1, 1, "RU", Date(), Date()),
                timezone = 18000,
                id = 1508291,
                name = "Chelyabinsk",
                cod = 200
            )
        )

        val result = weatherRepository.getCurrentWeather()

        Assert.assertEquals(expectedWeather, result)
    }
}
