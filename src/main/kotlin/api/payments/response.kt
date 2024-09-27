package api.payments

import api.serial.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class PaymentResponse(
    val paymentId: Long,
    val rentalId: Long,
    val amount: Double,
    var step: Int = 1,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val paymentDate: OffsetDateTime = OffsetDateTime.now(),
    val paymentMethod: String,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val createAt: OffsetDateTime = OffsetDateTime.now()
)
