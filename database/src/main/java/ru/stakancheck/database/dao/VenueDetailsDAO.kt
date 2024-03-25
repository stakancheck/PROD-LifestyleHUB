/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package skdev.wheelsservice.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import skdev.wheelsservice.database.models.VenueDetailsDBO


/**
 * Data Access Object for the venue_details table.
 */
@Dao
interface VenueDetailsDAO {

    @Query("SELECT * FROM venue_details WHERE id = :venueId")
    suspend fun getVenueDetailsById(venueId: String): VenueDetailsDBO

    @Query("SELECT * FROM venue_details WHERE id = :venueId")
    fun observeVenueDetailsById(venueId: String): Flow<VenueDetailsDBO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(venue: VenueDetailsDBO)

    @Query("DELETE FROM venue_details WHERE id = :venueId")
    suspend fun removeById(venueId: String)

    @Query("DELETE FROM venue_details")
    suspend fun clean()

}
