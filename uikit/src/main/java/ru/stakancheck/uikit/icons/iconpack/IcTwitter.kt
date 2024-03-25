/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.uikit.icons.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import ru.stakancheck.uikit.icons.IconPack

public val IconPack.IcTwitter: ImageVector
    get() {
        if (_icTwitter != null) {
            return _icTwitter!!
        }
        _icTwitter = Builder(
            name = "IcTwitter", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(4.594f, 4.984f)
                    curveTo(4.7751f, 4.963f, 4.9585f, 4.9918f, 5.1243f, 5.0674f)
                    curveTo(5.2902f, 5.143f, 5.4322f, 5.2625f, 5.535f, 5.413f)
                    curveTo(7.011f, 7.572f, 8.783f, 8.47f, 10.75f, 8.674f)
                    curveTo(10.846f, 7.833f, 11.073f, 7.002f, 11.5f, 6.27f)
                    curveTo(12.126f, 5.196f, 13.144f, 4.406f, 14.598f, 4.114f)
                    curveTo(16.608f, 3.71f, 18.138f, 4.438f, 19.025f, 5.329f)
                    lineTo(20.817f, 4.994f)
                    curveTo(21.0046f, 4.9589f, 21.1982f, 4.9781f, 21.3752f, 5.0494f)
                    curveTo(21.5521f, 5.1208f, 21.7049f, 5.2413f, 21.8157f, 5.3966f)
                    curveTo(21.9264f, 5.552f, 21.9903f, 5.7358f, 21.9999f, 5.9264f)
                    curveTo(22.0096f, 6.1169f, 21.9645f, 6.3062f, 21.87f, 6.472f)
                    lineTo(20.15f, 9.494f)
                    curveTo(20.307f, 13.855f, 19.095f, 16.899f, 16.511f, 18.996f)
                    curveTo(15.141f, 20.108f, 13.179f, 20.739f, 11.026f, 20.934f)
                    curveTo(8.856f, 21.13f, 6.403f, 20.893f, 3.965f, 20.181f)
                    curveTo(3.7567f, 20.1202f, 3.5738f, 19.9934f, 3.444f, 19.8195f)
                    curveTo(3.3141f, 19.6457f, 3.2443f, 19.4343f, 3.2451f, 19.2174f)
                    curveTo(3.2459f, 19.0004f, 3.3172f, 18.7895f, 3.4483f, 18.6167f)
                    curveTo(3.5795f, 18.4438f, 3.7633f, 18.3182f, 3.972f, 18.259f)
                    curveTo(5.198f, 17.91f, 6.132f, 17.609f, 6.975f, 17.082f)
                    curveTo(5.776f, 16.446f, 4.893f, 15.614f, 4.268f, 14.666f)
                    curveTo(3.4f, 13.348f, 3.078f, 11.878f, 3.014f, 10.553f)
                    curveTo(2.95f, 9.228f, 3.141f, 8.0f, 3.343f, 7.115f)
                    curveTo(3.458f, 6.61f, 3.592f, 6.104f, 3.777f, 5.62f)
                    curveTo(3.8423f, 5.4494f, 3.953f, 5.2999f, 4.0972f, 5.1878f)
                    curveTo(4.2414f, 5.0757f, 4.4126f, 5.0052f, 4.594f, 4.984f)
                    close()
                }
            }
        }
            .build()
        return _icTwitter!!
    }

private var _icTwitter: ImageVector? = null
