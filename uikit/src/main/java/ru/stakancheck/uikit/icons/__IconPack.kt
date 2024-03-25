/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.uikit.icons

import androidx.compose.ui.graphics.vector.ImageVector
import ru.stakancheck.uikit.icons.iconpack.IcFacebook
import ru.stakancheck.uikit.icons.iconpack.IcTwitter
import kotlin.collections.List as ____KtList

public object IconPack

private var __IconPack: ____KtList<ImageVector>? = null

public val IconPack.IconPack: ____KtList<ImageVector>
    get() {
        if (__IconPack != null) {
            return __IconPack!!
        }
        __IconPack = listOf(IcTwitter, IcFacebook)
        return __IconPack!!
    }
