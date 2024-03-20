/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.lifestylehub.data.repository

import ru.stakancheck.lifestylehub.data.source.remote.RemoteDataSource

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun getWeather(lat: Double, long: Double) = remoteDataSource.getWeather(lat, long)
}
