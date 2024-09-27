@file:Suppress("PropertyName")

package api.rentals

import api.serial.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Suppress("PropertyName")
@Serializable
data class RentalResponse(
    val rentalId: Long,
    val customerId: Long,
    val carId: Long,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val startDate: OffsetDateTime,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val endDate: OffsetDateTime,
    val totalPrice: Double,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val createAt: OffsetDateTime
)
