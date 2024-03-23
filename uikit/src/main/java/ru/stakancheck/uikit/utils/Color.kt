/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.uikit.utils

import androidx.compose.ui.graphics.Color


fun Color.withoutAlpha(): Color = this.copy(alpha = 1f)
