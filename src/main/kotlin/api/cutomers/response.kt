package api.cutomers

import api.serial.OffsetDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class CustomerResponse(
    @SerialName("customerId")
    val customerId: Long,
    @SerialName("firstName")
    val firstName: String,
    @SerialName("lastName")
    val lastName: String,
    @SerialName("email")
    val email: String,
    @SerialName("phoneNumber")
    val phoneNumber: String,
    @SerialName("driverLicense")
    val driverLicense: String,
    @SerialName("isBanned")
    var isBanned: Boolean = false,
    @SerialName("createAt")
    @Serializable(with = OffsetDateTimeSerializer::class)
    val createAt: OffsetDateTime
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