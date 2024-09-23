package api.cutomers

import kotlinx.serialization.Serializable

@Serializable
data class CustomerRequest(
    val first_name: String,
    val last_name: String,
    val email: String,
    val phone_number: String,
    val driver_license: String,
    var is_banned: Boolean = false,
)