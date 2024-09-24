package api.rentals


import api.HOST
import api.client
import api.cutomers.FailResponse
import api.cutomers.NotFoundResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.time.OffsetDateTime

suspend fun getRentals(
    customer_id: String? = null,
    car_id: String? = null,
    start_date: String? = null,
    end_date: String? = null,
    total_price: String? = null,
    sortBy: String = "rental_id", // Поле для сортировки по умолчанию
    sortDirection: String = "asc" // Направление сортировки по умолчанию
): List<RentalResponse> {

    val response: HttpResponse = client.request {
        url {
            method = HttpMethod.Get
            host = HOST // Замените на ваш хост
            port = 5022 // Убедитесь, что порту соответствует вашему серверу
            path("/api/Rentals/GetRentals") // Укажите только путь к API

            // Добавьте параметры запроса
            if (customer_id != null) parameters["customer_id"] = customer_id
            if (car_id != null) parameters["car_id"] = car_id
            if (start_date != null) parameters["start_date"] = start_date
            if (end_date != null) parameters["end_date"] = end_date
            if (total_price != null) parameters["total_price"] = total_price
            parameters["sortBy"] = sortBy
            parameters["sortDirection"] = sortDirection
        }
        contentType(ContentType.Application.Json)
    }

    println("Response Status: ${response.status.value}")
    return if (response.status.value in 200..299) {
        val result: List<RentalResponse> = response.body()
        println(result)
        result
    } else {
        println("Request failed with status: ${response.status.value}")
        val result: FailResponse = response.body()
        throw Exception(result.detail)
    }
}
suspend fun getRental(id: Long): RentalResponse {
    val response: HttpResponse = client.request{
        url {
            method = HttpMethod.Get
            protocol = URLProtocol.HTTP
            host = HOST
            port = 5022
            path("api/Rentals/GetRental/${id}")
        }
        contentType(ContentType.Application.Json)
    }

    if (response.status.value in 200..299) {
        val result: RentalResponse = response.body()
        return result
    }
    else {
        val result: NotFoundResponse = response.body()
        throw Exception(result.title)
    }
}

suspend fun putRental(rentalRequest: RentalResponse): RentalResponse {
    val response: HttpResponse = client.request {
        url {
            method = HttpMethod.Put
            protocol = URLProtocol.HTTP
            host = HOST
            port = 5022
            path("api/Rentals/UpdateRental/${rentalRequest.rental_id}")
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


suspend fun deleteRental(id: Long): RentalResponse {
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
        if (response.status.value == 204){
            val result = RentalResponse(
                rental_id = -1,
                customer_id = -1,
                car_id = -1,
                start_date = OffsetDateTime.now(),
                end_date = OffsetDateTime.now(),
                total_price = 0.0,
                create_at = OffsetDateTime.now()
            )
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