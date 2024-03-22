/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

@Composable
fun ColumnScope.Spacer(space: Dp) {
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(space))
}

@Composable
fun LazyItemScope.Spacer(space: Dp) {
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(space))
}


@Composable
fun RowScope.Spacer(space: Dp) {
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(space))
}
