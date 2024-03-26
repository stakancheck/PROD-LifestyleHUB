/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.stakancheck.database.models.LeisureEntryDBO


@Dao
interface LeisureEntryDAO {
    @Query("SELECT * FROM leisure_entries WHERE owner_id = :userId")
    suspend fun getLeisuresByUserId(userId: String): List<LeisureEntryDBO>

    @Query("SELECT * FROM leisure_entries WHERE id = :leisureId")
    suspend fun getLeisureById(leisureId: Long): LeisureEntryDBO

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(leisure: LeisureEntryDBO)

    @Query("DELETE FROM leisure_entries WHERE id = :leisureId")
    suspend fun removeById(leisureId: Long)

    @Query("DELETE FROM leisure_entries")
    suspend fun clean()
}