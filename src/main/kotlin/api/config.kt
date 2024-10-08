package api

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

const val HOST = "localhost"

val client = HttpClient(CIO){
    install(ContentNegotiation)
    {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
}