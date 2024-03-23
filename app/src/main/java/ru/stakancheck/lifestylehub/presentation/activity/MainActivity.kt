/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.lifestylehub.presentation.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import ru.stakancheck.common.permission.PermissionCheckerActivity
import ru.stakancheck.lifestylehub.presentation.navigating.BottomNavigationBar
import ru.stakancheck.lifestylehub.presentation.navigating.MainNavHost
import ru.stakancheck.uikit.theme.LifestyleHUBTheme
import ru.stakancheck.uikit.theme.SystemAppearance

class MainActivity : PermissionCheckerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LifestyleHUBTheme {
                // Setup system appearance
                SystemAppearance(
                    isSystemInDarkTheme(),
                    MaterialTheme.colorScheme.surfaceContainer
                )

                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.safeDrawingPadding(),
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) { innerPadding ->
                    MainNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
