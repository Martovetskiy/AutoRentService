package api.payments

import api.HOST
import api.client
import api.cutomers.FailResponse
import api.cutomers.NotFoundResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

suspend fun getPayments(
    rentalId: String? = null,
    amount: String? = null,
    step: String? = null,
    paymentDate: String? = null,
    paymentMethod: String? = null,
    sortBy: String = "paymentId", // Поле для сортировки по умолчанию
    sortDirection: String = "asc" // Направление сортировки по умолчанию
): List<PaymentResponse> {
    val response: HttpResponse = client.request {
        url {
            method = HttpMethod.Get
            host = HOST // Замените на ваш хост
            port = 5022 // Убедитесь, что порту соответствует вашему серверу
            path("/api/Payments/GetPayments") // Укажите только путь к API

            // Добавьте параметры запроса
            if (rentalId != null) parameters["rentalId"] = rentalId
            if (amount != null) parameters["amount"] = amount
            if (step != null) parameters["step"] = step
            if (paymentDate != null) parameters["paymentDate"] = paymentDate
            if (paymentMethod != null) parameters["paymentMethod"] = paymentMethod
            parameters["sortBy"] = sortBy
            parameters["sortDirection"] = sortDirection
        }
        contentType(ContentType.Application.Json)
    }

    return if (response.status.value in 200..299) {
        val result: List<PaymentResponse> = response.body()
        result
    } else {
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
            path("api/Payments/UpdatePayment/${paymentRequest.paymentId}")

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



suspend fun deletePayment(id: Long): PaymentResponse? {
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
        if (response.status.value == 200){
            val result = null
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