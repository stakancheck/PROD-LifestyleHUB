/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package skdev.wheelsservice.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import skdev.wheelsservice.database.dao.VenueDetailsDAO
import skdev.wheelsservice.database.models.VenueDetailsDBO
import skdev.wheelsservice.database.utils.Converters


class AppDatabase internal constructor(private val database: AppRoomDatabase) {
    val venueDetailsDao: VenueDetailsDAO
        get() = database.venueDetailsDao()
}

@Database(entities = [VenueDetailsDBO::class], version = 1)
@TypeConverters(Converters::class)
internal abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun venueDetailsDao(): VenueDetailsDAO
}

fun AppDatabase(applicationContext: Context): AppDatabase {
    val appRoomDatabase =
        Room.databaseBuilder(
            checkNotNull(applicationContext.applicationContext),
            AppRoomDatabase::class.java,
            "news"
        ).build()
    return AppDatabase(appRoomDatabase)
}