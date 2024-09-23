package api.cars

import api.serial.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class CarRequest(
    val make: String,
    val model: String,
    val year: Int,
    val color_hex: String,
    val price_per_day: Double,
    val number_plate: String,
    val status: String,
)