/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.stakancheck.api.HttpClient
import ru.stakancheck.api.WeatherApi
import ru.stakancheck.api.models.Language
import ru.stakancheck.api.utils.LoggerBridge
import ru.stakancheck.common.AndroidLogger
import ru.stakancheck.common.permission.PermissionChecker
import ru.stakancheck.data.repository.LocationRepository
import ru.stakancheck.data.repository.WeatherRepository
import ru.stakancheck.data.utils.Result
import java.util.Properties


@RunWith(AndroidJUnit4::class)
class WeatherRepositoryTest {
    private lateinit var weatherApi: WeatherApi
    private lateinit var locationRepository: LocationRepository
    private lateinit var weatherRepository: WeatherRepository

    private val properties = Properties().apply {
        load(
            Thread.currentThread().contextClassLoader?.getResourceAsStream("test.properties")
                ?: error("Properties file not found")
        )
    }

    private val apiKey = properties.getProperty("OPEN_WEATHER_API_KEY")

    @Before
    fun setup() {
        val logger = AndroidLogger()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        weatherApi = WeatherApi(
            HttpClient(
                object : LoggerBridge {
                    override fun log(tag: String, message: String) {
                        logger.d(tag = tag, message = message)
                    }
                }
            ),
            apiKey
        )
        locationRepository = LocationRepository(
            context = context,
            permissionChecker = object : PermissionChecker {
                override suspend fun checkPermissions(vararg permissions: String): kotlin.Result<Unit> {
                    return kotlin.Result.success(Unit)
                }
            },
            logger = logger
        )
        weatherRepository = WeatherRepository(
            weatherApi,
            locationRepository,
            logger
        )
    }

    @Test
    fun testGetCurrentWeather() = runBlocking {
        val result = weatherRepository.getCurrentWeather(Language.ENGLISH.code)

        // Проверьте, что результат не является ошибкой и содержит ожидаемые данные
        Assert.assertTrue(result is Result.Success)
        Assert.assertNotNull((result as Result.Success).data)
    }
}
