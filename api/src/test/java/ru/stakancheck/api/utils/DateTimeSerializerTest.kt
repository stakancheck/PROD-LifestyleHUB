/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.api.utils

import io.mockk.every
import io.mockk.mockk
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class DateTimeSerializerTest {

    private val encoder = mockk<Encoder>()
    private val decoder = mockk<Decoder>()

    @Test
    fun `DateTimeUTCSerializer serialize and deserialize correctly`() {
        val date = Date()
        val formattedDate = DateTimeUTCSerializer.formatter.format(date)

        every { encoder.encodeString(formattedDate) } returns Unit
        every { decoder.decodeString() } returns formattedDate

        DateTimeUTCSerializer.serialize(encoder, date)
        val deserializedDate = DateTimeUTCSerializer.deserialize(decoder)

        assertEquals(date.toInstant().epochSecond, deserializedDate.toInstant().epochSecond)
    }

    @Test
    fun `DateTimeUnixSerializer serialize and deserialize correctly`() {
        val date = Date()
        val epochSecond = date.toInstant().epochSecond

        every { encoder.encodeLong(epochSecond) } returns Unit
        every { decoder.decodeLong() } returns epochSecond

        DateTimeUnixSerializer.serialize(encoder, date)
        val deserializedDate = DateTimeUnixSerializer.deserialize(decoder)

        assertEquals(date.toInstant().epochSecond, deserializedDate.toInstant().epochSecond)
    }
}
