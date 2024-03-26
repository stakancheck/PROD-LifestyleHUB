/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.common.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.Date
import java.util.Locale

object DateTools {
    private val formatter: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.ROOT)

    fun provideFormattedDate(date: Date): String {
        val instant = date.toInstant()
        val zonedDateTime = instant.atZone(ZoneId.systemDefault())
        return formatter.format(Date.from(zonedDateTime.toInstant()))
    }
}