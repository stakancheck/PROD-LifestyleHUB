/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.repository

import ru.stakancheck.common.Logger
import ru.stakancheck.common.error.DataError
import ru.stakancheck.data.mappers.LeisureEntryDBOToLeisureEntryMapper
import ru.stakancheck.data.models.LeisureEntry
import ru.stakancheck.data.utils.Result
import ru.stakancheck.database.AppDatabase

class LeisureEntryRepository(
    private val database: AppDatabase,
    private val logger: Logger,
) {
    suspend fun getLeisureEntries(): Result<List<LeisureEntry>, DataError.Local> {
        return try {
            val entries = database.leisureEntryDao.getLeiseresByUserId("test")
                .map { LeisureEntryDBOToLeisureEntryMapper(it) }
            Result.Success(entries)
        } catch (e: Exception) {
            logger.e(tag = TAG, "getLeisureEntries: ${e.message}")
            Result.Error(DataError.Local.READ_ERROR)
        }
    }

    suspend fun removeLeisureById(id: Long): Result<Unit, DataError.Local> {
        return try {
            database.leisureEntryDao.removeById(id)
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e(tag = TAG, "removeLeisureById: ${e.message}")
            Result.Error(DataError.Local.WRITE_ERROR)
        }
    }

    companion object {
        const val TAG = "LeisureEntryRepository"
    }
}