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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.koin.compose.koinInject
import ru.stakancheck.common.permission.PermissionCheckerActivity
import ru.stakancheck.lifestylehub.presentation.navigating.BottomNavigationBar
import ru.stakancheck.lifestylehub.presentation.navigating.MainNavHost
import ru.stakancheck.lifestylehub.presentation.navigating.rememberAppState
import ru.stakancheck.uikit.components.ErrorPresenter
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
                val errorPresenter: ErrorPresenter = koinInject()
                val appState = rememberAppState(navController)

                Scaffold(
                    modifier = Modifier.safeDrawingPadding(),
                    bottomBar = {
                        if (appState.shouldShowBottomBar)
                            BottomNavigationBar(navController = navController)
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        MainNavHost(
                            navController = navController
                        )
                        errorPresenter.invoke()
                    }
                }
            }
        }
    }
}
