package api.cars

import api.HOST
import api.client
import api.cutomers.FailResponse
import api.cutomers.NotFoundResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.time.OffsetDateTime

suspend fun getCars(
    make: String? = null,
    model: String? = null,
    year: String? = null,
    color_hex: String? = null,
    price_per_day: String? = null,
    number_plate: String? = null,
    status: String? = null,
    sortBy: String = "customer_id", // Поле для сортировки по умолчанию
    sortDirection: String = "asc" // Направление сортировки по умолчанию
): List<CarResponse> {

    val response: HttpResponse = client.request {
        url {
            method = HttpMethod.Get
            host = HOST // Замените на ваш хост
            port = 5022 // Убедитесь, что порту соответствует вашему серверу
            path("/api/Cars/GetCars") // Укажите только путь к API

            // Добавьте параметры запроса
            if (make != null) parameters["make"] = make
            if (model != null) parameters["model"] = model
            if (year != null) parameters["year"] = year
            if (color_hex != null) parameters["color_hex"] = color_hex
            if (price_per_day != null) parameters["price_per_day"] = price_per_day
            if (number_plate != null) parameters["number_plate"] = number_plate
            if (status != null) parameters["status"] = status
            parameters["sortBy"] = sortBy
            parameters["sortDirection"] = sortDirection
        }
        contentType(ContentType.Application.Json)
    }

    println("Response Status: ${response.status.value}")
    return if (response.status.value in 200..299) {
        val result: List<CarResponse> = response.body()
        println(result)
        result
    } else {
        println("Request failed with status: ${response.status.value}")
        val result: FailResponse = response.body()
        throw Exception(result.detail)
    }
}

suspend fun getCar(id: Long): CarResponse {
    val response: HttpResponse = client.request{
        url {
            method = HttpMethod.Get
            protocol = URLProtocol.HTTP
            host = HOST
            port = 5022
            path("api/Cars/GetCar/${id}")
        }
        contentType(ContentType.Application.Json)
    }

    if (response.status.value in 200..299) {
        val result: CarResponse = response.body()
        return result
    }
    else {
        val result: NotFoundResponse = response.body()
        throw Exception(result.title)
    }
}

suspend fun postCar(carRequest: CarRequest): CarResponse {
    val response: HttpResponse = client.request{
        url {
            method = HttpMethod.Post
            protocol = URLProtocol.HTTP
            host = HOST
            port = 5022
            path("api/Cars/InsertCar")

        }
        contentType(ContentType.Application.Json)
        setBody(carRequest)
    }

    if (response.status.value in 200..299) {
        val result: CarResponse = response.body()
        return result
    }
    else {
        val result: FailResponse = response.body()
        throw Exception(result.detail)
    }
}

suspend fun putCar(carRequest: CarResponse): CarResponse {
    val response: HttpResponse = client.request{
        url {
            method = HttpMethod.Put
            protocol = URLProtocol.HTTP
            host = HOST
            port = 5022
            path("api/Cars/UpdateCar/${carRequest.car_id}")

        }
        contentType(ContentType.Application.Json)
        setBody(carRequest)
    }

    if (response.status.value in 200..299) {
        val result: CarResponse = response.body()
        return result
    }
    else {
        val result: FailResponse = response.body()
        throw Exception(result.detail)
    }
}

suspend fun deleteCar(id: Long): CarResponse {
    val response: HttpResponse = client.request{
        url {
            method = HttpMethod.Delete
            protocol = URLProtocol.HTTP
            host = HOST
            port = 5022
            path("api/Cars/DeleteCar/${id}")

        }
        contentType(ContentType.Application.Json)
    }

    if (response.status.value in 200..299) {
        if (response.status.value == 204){
            val result = CarResponse(
                car_id = -1,
                make = "",
                model = "",
                year = 0,
                color_hex = "",
                price_per_day = 0.0,
                number_plate = "",
                status = "",
                create_at = OffsetDateTime.now() // Create_at is not returned by the server for deleted cars, so we set it to current time by default.
            )
            return result
        }
        else {
            val result: CarResponse = response.body()
            return result
        }
    }
    else {
        val result: NotFoundResponse = response.body()
        throw Exception(result.title)
    }
}