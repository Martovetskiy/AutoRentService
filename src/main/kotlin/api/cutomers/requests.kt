package api.cutomers

import api.serial.OffsetDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class CustomerRequest(
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
    val createAt: OffsetDateTime = OffsetDateTime.now()
)