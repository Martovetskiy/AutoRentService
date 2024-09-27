package api.cars

import kotlinx.serialization.Serializable

@Serializable
data class CarRequest(
    val make: String,
    val model: String,
    val year: Int,
    val colorHex: String,
    val pricePerDay: Double,
    val numberPlate: String,
    val status: String,
)