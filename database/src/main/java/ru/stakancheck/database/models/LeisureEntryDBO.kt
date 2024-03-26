/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "leisure_entries")
data class LeisureEntryDBO(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long? = null,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "date") val date: Date,
    @Embedded(prefix = "interest_") val interest: Interest?,
    @ColumnInfo(name = "owner_id") val ownerId: String,
)


data class Interest(
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "type") val type: String
)

