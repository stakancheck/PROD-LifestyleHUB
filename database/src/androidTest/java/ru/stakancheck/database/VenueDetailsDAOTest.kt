/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package skdev.wheelsservice.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import skdev.wheelsservice.database.dao.VenueDetailsDAO
import skdev.wheelsservice.database.utils.TestSamples

@RunWith(AndroidJUnit4::class)
class VenueDetailsDAOTest {

    private lateinit var db: AppRoomDatabase
    private lateinit var dao: VenueDetailsDAO

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppRoomDatabase::class.java).build()
        dao = db.venueDetailsDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun `testCreateDatabaseAndRead`() = runBlocking {
        val venue = TestSamples.venueDetailsDBO1
        dao.insert(venue)
        val retrievedVenue = dao.getVenueDetailsById(venue.id)
        assertEquals(venue, retrievedVenue)
    }

    @Test
    fun `testObserveData`() = runBlocking {
        val venue = TestSamples.venueDetailsDBO1
        dao.insert(venue)
        val retrievedVenue = dao.observeVenueDetailsById(venue.id).first()
        assertEquals(venue, retrievedVenue)
    }

    @Test
    fun `testUpdateData`() = runBlocking {
        val venue = TestSamples.venueDetailsDBO1
        dao.insert(venue)
        val updatedVenue = TestSamples.venueDetailsDBO1.copy(name = "updatedName")
        dao.insert(updatedVenue)
        val retrievedVenue = dao.getVenueDetailsById(venue.id)
        assertEquals(updatedVenue, retrievedVenue)
    }

    @Test
    fun `testDelete`() = runBlocking {
        val venue = TestSamples.venueDetailsDBO1
        dao.insert(venue)
        dao.removeById(venue.id)
        val retrievedVenue = dao.getVenueDetailsById("sdasd")
        assertNull(retrievedVenue)
    }

    @Test
    fun `testGetEmptyListTypeConverter`() = runBlocking {
        val expectedValue = emptyList<String>()
        val venue = TestSamples.venueDetailsDBO1.copy(phrases = expectedValue)
        dao.insert(venue)
        val retrievedVenue = dao.getVenueDetailsById(venue.id)
        assertEquals(retrievedVenue.phrases, expectedValue)
    }
}