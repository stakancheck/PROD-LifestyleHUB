/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.repository

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import ru.stakancheck.common.Logger
import ru.stakancheck.common.error.DataError
import ru.stakancheck.common.permission.PermissionChecker
import ru.stakancheck.data.BuildConfig
import ru.stakancheck.data.mappers.AndroidLocationToLocationMapper
import ru.stakancheck.data.models.Location
import ru.stakancheck.data.models.checkRelevant
import ru.stakancheck.data.utils.Result
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import android.location.Location as AndroidLocation


/**
 * Repository to get location
 *
 * @param context - Android context
 * @param permissionChecker - Permission checker (for requesting location permission)
 * @param mainDispatcher - Coroutine dispatcher by default [Dispatchers.Main].immediate
 * @param logger - Logger
 */
class LocationRepository(
    private val context: Context,
    private val permissionChecker: PermissionChecker,
    private val mainDispatcher: CoroutineContext = Dispatchers.Main.immediate,
    private val logger: Logger,
) {
    private val savedLocation: Location? = null


    /**
     * Get location request
     *
     * 1) Method checks and request location permissions
     * 2) Checks if location services enabled
     * 3) Get location and return result
     *
     * _Location providers:_
     * - [LocationManager.GPS_PROVIDER]
     * - [LocationManager.NETWORK_PROVIDER]
     *
     * @return [Result] with [Location] or [DataError.Location] error
     */
    @SuppressLint("MissingPermission")
    suspend fun getLocation(): Result<Location, DataError.Location> {
        // Return custom location if debug mode
        if (BuildConfig.DEBUG) {
            return Result.Success(CUSTOM_DENUG_LOCATION)
        }

        // Return saved location if it's relevant
        if (savedLocation.checkRelevant()) {
            logger.d(tag = TAG, "getLocation: Saved location is relevant, returning it")
            return Result.Success(savedLocation!!)
        }

        // Check permission to get location
        val checkPermissionException = permissionChecker.checkPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ).exceptionOrNull()

        if ((checkPermissionException is PermissionChecker.PermissionsDeniedException && checkPermissionException.permissions.size > 1) ||
            (checkPermissionException != null && checkPermissionException !is PermissionChecker.PermissionsDeniedException)
        ) {
            // Return error if no persmission
            logger.e(
                tag = TAG,
                "getLocation: No permission to get location, exception -> $checkPermissionException"
            )
            return Result.Error(DataError.Location.NO_PERMISSION)
        }

        val locationService: LocationManager = context
            .getSystemService(LocationManager::class.java)

        val isLocationEnabled = locationService.run {
            isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                    isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }

        // Check if location services enabled
        if (!isLocationEnabled) {
            logger.e(tag = TAG, "getLocation: location services disabled")
            return Result.Error(DataError.Location.LOCATION_SERVICES_OFF)
        }

        return withContext(mainDispatcher) {
            suspendCancellableCoroutine {
                val callback = object : LocationListener {

                    override fun onLocationChanged(location: AndroidLocation) {
                        logger.d(tag = TAG, "getLocation: Location received -> $location")
                        it.resume(
                            Result.Success(AndroidLocationToLocationMapper(location))
                        )
                        locationService.removeUpdates(this)
                    }

                    override fun onProviderEnabled(provider: String) {}
                    override fun onProviderDisabled(provider: String) {}
                }

                it.invokeOnCancellation { locationService.removeUpdates(callback) }

                locationService.apply {
                    requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0F, callback)
                    requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0F, callback)
                }
            }
        }
    }

    companion object {
        const val TAG = "LocationRepository"

        /**
         * Las Vegas center location
         * Uses for collecting more data
         */
        val CUSTOM_DENUG_LOCATION = Location(36.172499, -115.146701)
    }
}
