/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.lifestylehub.presentation.navigating


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import ru.stakancheck.lifestylehub.R

sealed class BottomBarItem(
    val route: String,
    val icon: ImageVector,
    val activeIcon: ImageVector,
    val labelResource: Int
) {
    data object Main : BottomBarItem(
        "main",
        Icons.Outlined.Home,
        Icons.Filled.Home,
        R.string.label_bottom_main
    )

    data object Leisure : BottomBarItem(
        "leisure",
        Icons.Outlined.ConfirmationNumber,
        Icons.Filled.ConfirmationNumber,
        R.string.label_bottom_leisure
    )

    data object Profile : BottomBarItem(
        "profile",
        Icons.Outlined.Person,
        Icons.Filled.Person,
        R.string.label_bottom_profile
    )

    companion object {
        val values: List<BottomBarItem> = listOf(Main, Leisure, Profile)
    }
}