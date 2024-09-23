package api.payments

import api.serial.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class PaymentRequest(
    val rental_id: Long,
    val amount: Double,
    var step: Int = 1,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val payment_date: OffsetDateTime = OffsetDateTime.now(),
    val payment_method: String,
)