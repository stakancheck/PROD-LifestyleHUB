/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.common.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.Px
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

fun Context.spToPx(sp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics)
}

@Px
fun Context.dpToPx(dp: Float): Int {
    return dpToPx(dp, resources)
}

@Px
fun dpToPx(dp: Float, density: Density): Float {
    return dp * density.density
}

@Px
fun dpToPx(dp: Float, resources: Resources): Int {
    return dpToPxAsFloat(dp, resources).roundToInt()
}

@Px
@Composable
fun Dp.toPx(): Int {
    return dpToPxAsFloat(this.value, LocalContext.current.resources).roundToInt()
}

@Px
fun dpToPxAsFloat(dp: Float, resources: Resources): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
}

@Composable
fun pxToDp(px: Int): Dp {
    return with(LocalContext.current.resources.displayMetrics) {
        px / density
    }.dp
}
