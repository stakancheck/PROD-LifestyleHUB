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
import ru.stakancheck.main.feed.presentation.MainFeedScreen


@Composable
fun MainFeedTab(
    navController: NavController
) {
    MainFeedScreen(
        navigateToVenueDetails = { venueId ->
            navController.navigate("venue/$venueId")
        }
    )
}
