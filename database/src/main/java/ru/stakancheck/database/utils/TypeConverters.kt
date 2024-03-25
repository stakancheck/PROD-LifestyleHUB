/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.database.utils

import androidx.room.TypeConverter
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.stakancheck.database.models.Category
import ru.stakancheck.database.models.TimeFrame
import java.util.Date

internal class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromStringToListString(value: String?): List<String> {
        return try {
            value?.let { Json.decodeFromString<List<String>>(it) } ?: emptyList()
        } catch (e: SerializationException) {
            emptyList()
        }
    }

    @TypeConverter
    fun listStringToString(value: List<String>?): String {
        return value?.let { Json.encodeToString(it) } ?: ""
    }

    @TypeConverter
    fun fromStringToListCategory(value: String?): List<Category> {
        return try {
            value?.let { Json.decodeFromString<List<Category>>(it) } ?: emptyList()
        } catch (e: SerializationException) {
            emptyList()
        }
    }

    @TypeConverter
    fun listCategoryToString(value: List<Category>?): String {
        return value?.let { Json.encodeToString(it) } ?: ""
    }

    @TypeConverter
    fun fromStringToListTimeFrame(value: String?): List<TimeFrame> {
        return try {
            value?.let { Json.decodeFromString<List<TimeFrame>>(it) } ?: emptyList()
        } catch (e: SerializationException) {
            emptyList()
        }
    }

    @TypeConverter
    fun listTimeFrameToString(value: List<TimeFrame>?): String {
        return value?.let { Json.encodeToString(it) } ?: ""
    }
}
