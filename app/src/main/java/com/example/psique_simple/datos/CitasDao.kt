package com.example.psique_simple.datos

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CitasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(cita: Cita)

    @Delete
    suspend fun borrar(cita: Cita)

    @Query("SELECT * FROM citas_agendadas ORDER BY fecha, hora")
    fun obtenerTodas(): Flow<List<Cita>>
}