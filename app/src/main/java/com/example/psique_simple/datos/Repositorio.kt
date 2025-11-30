package com.example.psique_simple.datos

import com.example.psique_simple.api.RetrofitClient // Importa el cliente API
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow // Necesario para crear un Flow desde la red

class Repositorio(private val citasDao: CitasDao) {
    // NOTA: Mantenemos el DAO como dependencia, pero no lo usaremos por ahora.
    // En una app real lo usarías para manejar caché offline.

    // 1. Obtener todas las citas (AHORA DE LA RED)
    val todasLasCitas: Flow<List<Cita>> = flow {
        try {
            // Llama al endpoint GET /api/citas
            val listaRemota = RetrofitClient.psiquiatriaApi.obtenerCitas()
            emit(listaRemota)
        } catch (e: Exception) {
            e.printStackTrace()
            // En caso de fallo de red, emitimos una lista vacía.
            emit(emptyList())
        }
    }

    // 2. Insertar una cita (AHORA EN LA RED)
    suspend fun insertarCita(cita: Cita) {
        try {
            // Llama al endpoint POST /api/citas
            // Ojo: Enviamos la cita con ID=0 para que el servidor la autogenere.
            RetrofitClient.psiquiatriaApi.agendarCita(cita)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 3. Borrar una cita (AHORA EN LA RED)
    suspend fun borrarCita(cita: Cita) {
        try {
            // Llama al endpoint DELETE /api/citas/{id}
            RetrofitClient.psiquiatriaApi.borrarCita(cita.id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}