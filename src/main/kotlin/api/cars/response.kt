package api.cars

import api.serial.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class CarResponse(
    val car_id: Long,
    val make: String,
    val model: String,
    val year: Int,
    val color_hex: String,
    val price_per_day: Double,
    val number_plate: String,
    val status: String,
    @Serializable(with = OffsetDateTimeSerializer::class)
    var create_at: OffsetDateTime = OffsetDateTime.now()
)
