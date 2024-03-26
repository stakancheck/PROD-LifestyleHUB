/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.lifestylehub.presentation.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ru.stakancheck.leisure.list.presentation.LeisureListUI


@Composable
fun LeisureTab(
    navController: NavController
) {
    LeisureListUI(
        navigateToVenueDetails = { venueId ->
            navController.navigate("venue/$venueId")
        },
        navigateToLeisureEdit = { leisureId ->
            navController.navigate("leisure_edit${leisureId?.let { "?leisureId=$leisureId" } ?: ""}")
        }
    )
}
