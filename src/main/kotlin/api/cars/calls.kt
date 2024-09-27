package api.cars

import api.HOST
import api.client
import api.cutomers.FailResponse
import api.cutomers.NotFoundResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

suspend fun getCars(
    make: String? = null,
    model: String? = null,
    year: String? = null,
    colorHex: String? = null,
    pricePerDay: String? = null,
    numberPlate: String? = null,
    status: String? = null,
    sortBy: String = "carId", // Поле для сортировки по умолчанию
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
            if (colorHex != null) parameters["colorHex"] = colorHex
            if (pricePerDay != null) parameters["pricePerDay"] = pricePerDay
            if (numberPlate != null) parameters["numberPlate"] = numberPlate
            if (status != null) parameters["status"] = status
            parameters["sortBy"] = sortBy
            parameters["sortDirection"] = sortDirection
        }
        contentType(ContentType.Application.Json)
    }

    return if (response.status.value in 200..299) {
        val result: List<CarResponse> = response.body()
        result
    } else {
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
            path("api/Cars/UpdateCar/${carRequest.carId}")

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

suspend fun deleteCar(id: Long): CarResponse? {
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
        if (response.status.value == 200){
            val result = null
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