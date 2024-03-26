/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.lifestylehub.presentation.navigating

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
        composable(
            BottomBarItem.Main.route,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) {
            MainFeedTab(
                navController = navController
            )
        }

        composable(
            BottomBarItem.Leisure.route,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) {
            LeisureTab(
                navController = navController
            )
        }

        composable(
            BottomBarItem.Profile.route,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) {
            UserProfileTab()
        }

        composable(
            "venue/{venueId}",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { backStackEntry ->
            VenueDetailsUI(
                venueId = backStackEntry.arguments?.getString("venueId")
                    ?: throw IllegalStateException("No venueId")
            )
        }
    }