/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.common.permission

/**
 * PermissionChecker is an interface for checking permissions.
 */
interface PermissionChecker {

    /**
     * Checks permissions
     *
     * @return [Result] either successful with unit type or with
     * [PermissionChecker.PermissionsDeniedException] that contains set of denied permissions
     */
    suspend fun checkPermissions(vararg permissions: String): Result<Unit>

    class PermissionsDeniedException(val permissions: Set<String>) : Exception()
}
