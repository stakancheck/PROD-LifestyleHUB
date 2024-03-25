/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.stakancheck.database.dao.LeisureEntryDAO
import ru.stakancheck.database.dao.VenueDetailsDAO
import ru.stakancheck.database.models.LeisureEntryDBO
import ru.stakancheck.database.models.VenueDetailsDBO
import ru.stakancheck.database.utils.Converters


class AppDatabase internal constructor(private val database: AppRoomDatabase) {
    val venueDetailsDao: VenueDetailsDAO
        get() = database.venueDetailsDao()
}

@Database(entities = [VenueDetailsDBO::class, LeisureEntryDBO::class], version = 1)
@TypeConverters(Converters::class)
internal abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun venueDetailsDao(): VenueDetailsDAO
    abstract fun leisureEntryDao(): LeisureEntryDAO
}

fun AppDatabase(applicationContext: Context): AppDatabase {
    val appRoomDatabase =
        Room.databaseBuilder(
            checkNotNull(applicationContext.applicationContext),
            AppRoomDatabase::class.java,
            "app_database"
        ).build()
    return AppDatabase(appRoomDatabase)
}