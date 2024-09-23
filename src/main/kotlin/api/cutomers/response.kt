package api.cutomers

import api.serial.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class CustomerResponse(
    val customer_id: Long,
    val first_name: String,
    val last_name: String,
    val email: String,
    val phone_number: String,
    val driver_license: String,
    val is_banned: Boolean,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val create_at: OffsetDateTime
)

@Serializable
data class FailResponse(
    val detail: String
)

@Serializable
data class NotFoundResponse(
    val type: String,
    val title: String,
    val status: Int,
    val traceId: String
)