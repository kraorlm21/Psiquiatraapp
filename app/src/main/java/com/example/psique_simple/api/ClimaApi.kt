package com.example.psique_simple.api

import retrofit2.http.GET

// 1. Modelos de datos para leer el JSON de Open-Meteo
data class ClimaResponse(
    val current_weather: CurrentWeather
)

data class CurrentWeather(
    val temperature: Double,
    val weathercode: Int,
    val windspeed: Double
)

// 2. Interfaz con la llamada a la API
interface ClimaApi {
    // Coordenadas de Santiago: Lat -33.4489, Long -70.6693
    @GET("v1/forecast?latitude=-33.4489&longitude=-70.6693&current_weather=true")
    suspend fun obtenerClimaSantiago(): ClimaResponse
}