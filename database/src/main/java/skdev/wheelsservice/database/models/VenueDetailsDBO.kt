/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package skdev.wheelsservice.database.models


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Entity(tableName = "venue_details")
data class VenueDetailsDBO(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "cached_at") val cachedAt: Int,
    @ColumnInfo(name = "name") val name: String,
    @Embedded(prefix = "contact_") val contact: Contact,
    @Embedded(prefix = "location_") val location: Location,
    /**
     * Field will be serialized as a JSON object
     */
    @ColumnInfo(name = "categories") val categories: List<Category>,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "likes_count") val likesCount: Int,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "short_url") val shortUrl: String?,
    @ColumnInfo(name = "best_photo_url") val bestPhotoUrl: String?,
    @ColumnInfo(name = "photo_urls") val photoUrls: String,
    @ColumnInfo(name = "phrases") val phrases: String,
    @ColumnInfo(name = "reasons") val reasons: String,
    @Embedded(prefix = "hours_") val hours: Hours
)

data class Contact(
    @ColumnInfo(name = "phone") val phone: String?,
    @ColumnInfo(name = "formatted_phone") val formattedPhone: String?,
    @ColumnInfo(name = "twitter") val twitter: String?,
    @ColumnInfo(name = "instagram") val instagram: String?,
    @ColumnInfo(name = "facebook") val facebook: String?,
    @ColumnInfo(name = "facebook_username") val facebookUsername: String?,
    @ColumnInfo(name = "facebook_name") val facebookName: String?
)

data class Location(
    @ColumnInfo(name = "latitude") val lat: Double,
    @ColumnInfo(name = "longitude") val lng: Double,
    @ColumnInfo(name = "formatted_address") val formattedAddress: List<String>
)

@Serializable
data class Category(
    val id: String,
    val shortName: String,
    val iconUrl: String
)

data class Hours(
    @ColumnInfo(name = "is_open") val isOpen: Boolean,
    @ColumnInfo(name = "status") val status: String,
    /**
     * Embedded field will be serialized as a JSON object
     */
    @ColumnInfo(name = "time_frames") val timeFrames: List<TimeFrame>
)

@Serializable
data class TimeFrame(
    val period: String,
    val times: List<String>
)
