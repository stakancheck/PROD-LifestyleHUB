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
import ru.stakancheck.lifestylehub.presentation.feature_leisure.LeisureScreen
import ru.stakancheck.lifestylehub.presentation.feature_main_feed.MainFeedScreen
import ru.stakancheck.lifestylehub.presentation.feature_user_profile.UserProfileScreen

@Composable
fun MainNavHost(navController: NavHostController, modifier: Modifier = Modifier) =
    NavHost(
        navController = navController,
        startDestination = BottomBarItem.Main.route,
        modifier = modifier
    ) {
        composable(BottomBarItem.Main.route) {
            MainFeedScreen()
        }

        composable(BottomBarItem.Leisure.route) {
            LeisureScreen()
        }

        composable(BottomBarItem.Profile.route) {
            UserProfileScreen()
        }
    }