package com.example.psique_simple.api

import com.example.psique_simple.datos.Cita
import retrofit2.Response
import retrofit2.http.*

interface PsiquiatriaApiService {

    // 1. Obtener todas las citas (GET)
    @GET("api/citas")
    suspend fun obtenerCitas(): List<Cita>

    // 2. Agendar una nueva cita (POST)
    @POST("api/citas")
    suspend fun agendarCita(@Body cita: Cita): Cita

    // 3. Borrar una cita (DELETE)
    // Ojo: Usamos Path variable para enviar el ID
    @DELETE("api/citas/{id}")
    suspend fun borrarCita(@Path("id") id: Int): Response<Void>
}