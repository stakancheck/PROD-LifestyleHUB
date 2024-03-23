/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.common.utils

import android.content.Context
import android.content.pm.PackageManager

fun Context.isPermissionDenied(permission: String): Boolean =
    checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED

fun Context.isPermissionGranted(permission: String): Boolean =
    checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
