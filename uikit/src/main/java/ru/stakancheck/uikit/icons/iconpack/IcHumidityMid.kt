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
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import ru.stakancheck.uikit.icons.IconPack

public val IconPack.IcHumidityMid: ImageVector
    get() {
        if (_icHumidityMid != null) {
            return _icHumidityMid!!
        }
        _icHumidityMid = Builder(
            name = "IcHumidityMid", defaultWidth = 24.0.dp, defaultHeight =
            24.0.dp, viewportWidth = 960.0f, viewportHeight = 960.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(480.0f, 839.999f)
                quadToRelative(-124.687f, 0.0f, -212.343f, -86.118f)
                quadToRelative(-87.656f, -86.118f, -87.656f, -209.678f)
                quadToRelative(0.0f, -58.972f, 23.347f, -112.818f)
                quadToRelative(23.346f, -53.846f, 64.499f, -95.538f)
                lineTo(480.0f, 127.694f)
                lineToRelative(212.153f, 208.153f)
                quadToRelative(41.153f, 41.692f, 64.499f, 95.554f)
                quadToRelative(23.347f, 53.861f, 23.347f, 112.874f)
                quadToRelative(0.0f, 123.647f, -87.656f, 209.686f)
                quadTo(604.687f, 839.999f, 480.0f, 839.999f)
                close()
                moveTo(240.0f, 544.385f)
                horizontalLineToRelative(480.0f)
                quadToRelative(0.0f, -47.0f, -18.0f, -89.64f)
                reflectiveQuadTo(650.0f, 380.0f)
                lineTo(480.0f, 212.0f)
                lineTo(310.0f, 380.0f)
                quadToRelative(-34.0f, 32.105f, -52.0f, 74.745f)
                quadToRelative(-18.0f, 42.64f, -18.0f, 89.64f)
                close()
            }
        }
            .build()
        return _icHumidityMid!!
    }

private var _icHumidityMid: ImageVector? = null
