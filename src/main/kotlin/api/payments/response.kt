package api.payments

import api.serial.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class PaymentResponse(
    val payment_id: Long,
    val rental_id: Long,
    val amount: Double,
    var step: Int = 1,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val payment_date: OffsetDateTime = OffsetDateTime.now(),
    val payment_method: String,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val create_at: OffsetDateTime = OffsetDateTime.now()
)
