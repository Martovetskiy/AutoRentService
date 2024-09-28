package api.rentals


import api.HOST
import api.client
import api.cutomers.FailResponse
import api.cutomers.NotFoundResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

suspend fun getRentals(
    firstName: String? = null,
    email: String? = null,
    make: String? = null,
    model: String? = null,
    startDate: String? = null,
    endDate: String? = null,
    totalPrice: String? = null,
    createdAt: String? = null,
    sortBy: String = "firstName", // Поле для сортировки по умолчанию
    sortDirection: String = "asc" // Направление сортировки по умолчанию
): List<RentalResponse> {

    val response: HttpResponse = client.request {
        url {
            method = HttpMethod.Get
            host = HOST // Замените на ваш хост
            port = 5022 // Убедитесь, что порту соответствует вашему серверу
            path("/api/Rentals/GetRentals") // Укажите только путь к API

            // Добавьте параметры запроса
            if (firstName != null) parameters["firstName"] = firstName
            if (email != null) parameters["email"] = email
            if (make != null) parameters["make"] = make
            if (model != null) parameters["model"] = model
            if (startDate != null) parameters["startDate"] = startDate
            if (endDate != null) parameters["endDate"] = endDate
            if (totalPrice != null) parameters["totalPrice"] = totalPrice
            if (createdAt != null) parameters["createdAt"] = createdAt
            parameters["sortBy"] = sortBy
            parameters["sortDirection"] = sortDirection
        }
        contentType(ContentType.Application.Json)
    }

    return if (response.status.value in 200..299) {
        val result: List<RentalResponse> = response.body()
        result
    } else {
        val result: FailResponse = response.body()
        throw Exception(result.detail)
    }
}
//suspend fun getRental(id: Long): RentalResponse {
//    val response: HttpResponse = client.request{
//        url {
//            method = HttpMethod.Get
//            protocol = URLProtocol.HTTP
//            host = HOST
//            port = 5022
//            path("api/Rentals/GetRental/${id}")
//        }
//        contentType(ContentType.Application.Json)
//    }
//
//    if (response.status.value in 200..299) {
//        val result: RentalResponse = response.body()
//        return result
//    }
//    else {
//        val result: NotFoundResponse = response.body()
//        throw Exception(result.title)
//    }
//}

suspend fun putRental(rentalRequest: RentalResponse): RentalResponse {
    val response: HttpResponse = client.request {
        url {
            method = HttpMethod.Put
            protocol = URLProtocol.HTTP
            host = HOST
            port = 5022
            path("api/Rentals/UpdateRental/${rentalRequest.rentalId}")
        }
        contentType(ContentType.Application.Json)
        setBody(rentalRequest)
    }

    if (response.status.value in 200..299) {
        val result: RentalResponse = response.body()
        return result
    } else {
        val result: FailResponse = response.body()
        throw Exception(result.detail)
    }
}


suspend fun postRental(rentalRequest: RentalRequest): RentalResponse {
    val response: HttpResponse = client.request{
        url {
            method = HttpMethod.Post
            protocol = URLProtocol.HTTP
            host = HOST
            port = 5022
            path("api/Rentals/InsertRental")

        }
        contentType(ContentType.Application.Json)
        setBody(rentalRequest)
    }

    if (response.status.value in 200..299) {
        val result: RentalResponse = response.body()
        return result
    }
    else {
        val result: FailResponse = response.body()
        throw Exception(result.detail)
    }
}


suspend fun deleteRental(id: Long): RentalResponse? {
    val response: HttpResponse = client.request{
        url {
            method = HttpMethod.Delete
            protocol = URLProtocol.HTTP
            host = HOST
            port = 5022
            path("api/Rentals/DeleteRental/${id}")

        }
        contentType(ContentType.Application.Json)
    }

    if (response.status.value in 200..299) {
        if (response.status.value == 200){
            val result = null
            return result
        }
        else {
            val result: RentalResponse = response.body()
            return result
        }
    }
    else {
        val result: NotFoundResponse = response.body()
        throw Exception(result.title)
    }
}