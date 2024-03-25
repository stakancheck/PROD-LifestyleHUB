/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.lifestylehub.presentation.navigating

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.stakancheck.lifestylehub.presentation.screens.LeisureTab
import ru.stakancheck.lifestylehub.presentation.screens.MainFeedTab
import ru.stakancheck.lifestylehub.presentation.screens.UserProfileTab
import ru.stakancheck.venue.details.presentation.VenueDetailsUI

@Composable
fun MainNavHost(navController: NavHostController, modifier: Modifier = Modifier) =
    NavHost(
        navController = navController,
        startDestination = BottomBarItem.Main.route,
        modifier = modifier
    ) {
        composable(BottomBarItem.Main.route) {
            MainFeedTab(
                navController = navController
            )
        }

        composable(BottomBarItem.Leisure.route) {
            LeisureTab()
        }

        composable(BottomBarItem.Profile.route) {
            UserProfileTab()
        }

        composable("venue/{venueId}") { backStackEntry ->
            VenueDetailsUI(
                venueId = backStackEntry.arguments?.getString("venueId")
                    ?: throw IllegalStateException("No venueId")
            )
        }
    }