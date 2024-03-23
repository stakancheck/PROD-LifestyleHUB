/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.common.permission

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import org.koin.android.ext.android.get

/**
 * Base activity for all activities for checking permissions.
 *
 * Attaches to [PermissionCheckerImpl] and transfers permission result
 */
abstract class PermissionCheckerActivity : ComponentActivity() {

    private val permissionChecker = get<PermissionCheckerImpl>()

    /**
     * Register for permission result
     *
     * @param result The result of the permission request
     */
    val resultLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { result: Map<String, Boolean> ->
        permissionChecker.onPermissionResult(result)
        onPermissionResult(result)
    }

    override fun onStart() {
        super.onStart()
        // Attach checker
        permissionChecker.attach(this@PermissionCheckerActivity)
    }

    /**
     * Callback for permission result
     *
     * @param result The result of the permission request
     */
    open fun onPermissionResult(result: Map<String, Boolean>) {

    }
}
