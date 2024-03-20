/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.lifestylehub.data.source.remote

import ru.stakancheck.lifestylehub.data.api.WeatherApi

class RemoteDataSource(
    private val weatherApi: WeatherApi
) {

    suspend fun getWeather(lat: Double, long: Double) = weatherApi.getWeather(lat, long)

}