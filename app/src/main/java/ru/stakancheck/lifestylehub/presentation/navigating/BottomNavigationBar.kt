/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.lifestylehub.presentation.navigating

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.stakancheck.uikit.theme.Dimens
import ru.stakancheck.uikit.theme.Radius

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        BottomBarItem.values.forEach { item ->
            Box(
                modifier = Modifier
                    .padding(horizontal = Dimens.spaceSmall)
                    .clip(RoundedCornerShape(Radius.large))
                    .selectable(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        role = Role.Tab,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = LocalIndication.current,
                    )
                    .height(64.dp)
                    .weight(1f)
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(28.dp),
                    tint = if (currentRoute == item.route) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    imageVector = if (currentRoute == item.route) item.activeIcon else item.icon,
                    contentDescription = stringResource(id = item.labelResource)
                )
            }
        }
    }
}