package api.rentals

import api.serial.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class RentalRequest(
    val customer_id: Long,
    val car_id: Long,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val start_date: OffsetDateTime,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val end_date: OffsetDateTime,
    val total_price: Double = 0.0
)