package api.rentals

import api.serial.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class RentalRequest(
    val customerId: Long,
    val carId: Long,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val startDate: OffsetDateTime,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val endDate: OffsetDateTime,
    val totalPrice: Double = 0.0
)