/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.stakancheck.database.AppRoomDatabase
import ru.stakancheck.database.dao.LeisureEntryDAO
import ru.stakancheck.database.utils.TestSamples

@RunWith(AndroidJUnit4::class)
class LeisureEntryDAOTest {

    private lateinit var db: AppRoomDatabase
    private lateinit var dao: LeisureEntryDAO

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppRoomDatabase::class.java).build()
        dao = db.leisureEntryDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun `testInsertAndRead`() = runBlocking {
        val leisureEntry = TestSamples.leisureEntryDBO1
        dao.insert(leisureEntry)
        val retrievedEntry = dao.getLeiseresByUserId(leisureEntry.ownerId)
        assertEquals(leisureEntry.date, retrievedEntry.first().date)
    }

    @Test
    fun `testUpdate`() = runBlocking {
        val leisureEntry = TestSamples.leisureEntryDBO1.copy(id = 2)
        dao.insert(leisureEntry)
        val updatedEntry = leisureEntry.copy(title = "Updated Title")
        dao.insert(updatedEntry)
        val retrievedEntry = dao.getLeiseresByUserId(updatedEntry.ownerId)
        assertEquals(listOf(updatedEntry), retrievedEntry)
    }

    @Test
    fun `testDelete`() = runBlocking {
        val leisureEntry = TestSamples.leisureEntryDBO1.copy(id = 3)
        dao.insert(leisureEntry)
        dao.removeById(leisureEntry.id!!)
        val retrievedEntry = dao.getLeiseresByUserId(leisureEntry.ownerId)
        assertNull(retrievedEntry.find { it.id == leisureEntry.id })
    }
}
