package api.payments

import api.HOST
import api.client
import api.cutomers.FailResponse
import api.cutomers.NotFoundResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.time.OffsetDateTime

suspend fun getPayments(
    rental_id: String? = null,
    amount: String? = null,
    step: String? = null,
    payment_date: String? = null,
    payment_method: String? = null,
    sortBy: String = "rental_id", // Поле для сортировки по умолчанию
    sortDirection: String = "asc" // Направление сортировки по умолчанию
): List<PaymentResponse> {

    val response: HttpResponse = client.request {
        url {
            method = HttpMethod.Get
            host = HOST // Замените на ваш хост
            port = 5022 // Убедитесь, что порту соответствует вашему серверу
            path("/api/Payments/GetPayments") // Укажите только путь к API

            // Добавьте параметры запроса
            if (rental_id != null) parameters["rental_id"] = rental_id
            if (amount != null) parameters["amount"] = amount
            if (step != null) parameters["step"] = step
            if (payment_date != null) parameters["payment_date"] = payment_date
            if (payment_method != null) parameters["payment_method"] = payment_method
            parameters["sortBy"] = sortBy
            parameters["sortDirection"] = sortDirection
        }
        contentType(ContentType.Application.Json)
    }

    println("Response Status: ${response.status.value}")
    return if (response.status.value in 200..299) {
        val result: List<PaymentResponse> = response.body()
        println(result)
        result
    } else {
        println("Request failed with status: ${response.status.value}")
        val result: FailResponse = response.body()
        throw Exception(result.detail)
    }
}

suspend fun getPayment(id: Long): PaymentResponse {
    val response: HttpResponse = client.request{
        url {
            method = HttpMethod.Get
            protocol = URLProtocol.HTTP
            host = HOST
            port = 5022
            path("api/Payments/GetPayment/${id}")
        }
        contentType(ContentType.Application.Json)
    }

    if (response.status.value in 200..299) {
        val result: PaymentResponse = response.body()
        return result
    }
    else {
        val result: NotFoundResponse = response.body()
        throw Exception(result.title)
    }
}

suspend fun postPayment(paymentRequest: PaymentRequest): PaymentResponse {
    val response: HttpResponse = client.request{
        url {
            method = HttpMethod.Post
            protocol = URLProtocol.HTTP
            host = HOST
            port = 5022
            path("api/Payments/InsertPayment")

        }
        contentType(ContentType.Application.Json)
        setBody(paymentRequest)
    }

    if (response.status.value in 200..299) {
        val result: PaymentResponse = response.body()
        return result
    }
    else {
        val result: FailResponse = response.body()
        throw Exception(result.detail)
    }
}

suspend fun putPayment(paymentRequest: PaymentResponse): PaymentResponse {
    val response: HttpResponse = client.request{
        url {
            method = HttpMethod.Put
            protocol = URLProtocol.HTTP
            host = HOST
            port = 5022
            path("api/Payments/UpdatePayment/${paymentRequest.payment_id}")

        }
        contentType(ContentType.Application.Json)
        setBody(paymentRequest)
    }

    if (response.status.value in 200..299) {
        val result: PaymentResponse = response.body()
        return result
    }
    else {
        val result: FailResponse = response.body()
        throw Exception(result.detail)
    }
}



suspend fun deletePayment(id: Long): PaymentResponse {
    val response: HttpResponse = client.request{
        url {
            method = HttpMethod.Delete
            protocol = URLProtocol.HTTP
            host = HOST
            port = 5022
            path("api/Payments/DeletePayment/${id}")

        }
        contentType(ContentType.Application.Json)
    }

    if (response.status.value in 200..299) {
        if (response.status.value == 204){
            val result = PaymentResponse(
                payment_id = -1,
                rental_id = -1,
                amount = 0.0,
                step = 0,
                payment_date = OffsetDateTime.now(),
                payment_method = ""
            )
            return result
        }
        else {
            val result: PaymentResponse = response.body()
            return result
        }
    }
    else {
        val result: NotFoundResponse = response.body()
        throw Exception(result.title)
    }
}