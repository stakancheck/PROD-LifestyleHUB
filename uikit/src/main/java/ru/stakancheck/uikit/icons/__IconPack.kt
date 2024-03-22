/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.uikit.icons

import androidx.compose.ui.graphics.vector.ImageVector
import ru.stakancheck.uikit.icons.iconpack.IcHumidityHigh
import ru.stakancheck.uikit.icons.iconpack.IcHumidityLow
import ru.stakancheck.uikit.icons.iconpack.IcHumidityMid
import kotlin.collections.List as ____KtList

public object IconPack

private var __AllIcons: ____KtList<ImageVector>? = null

public val IconPack.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = listOf(IcHumidityHigh, IcHumidityMid, IcHumidityLow)
        return __AllIcons!!
    }
