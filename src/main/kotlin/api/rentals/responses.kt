@file:Suppress("PropertyName")

package api.rentals

import api.cars.CarResponse
import api.cutomers.CustomerResponse
import api.serial.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Suppress("PropertyName")
@Serializable
data class RentalResponse(
    val rentalId: Long,
    val customerId: Long,
    val customer: CustomerResponse? = null,
    val carId: Long,
    val car: CarResponse? = null,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val startDate: OffsetDateTime,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val endDate: OffsetDateTime,
    val totalPrice: Double,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val createAt: OffsetDateTime
)
