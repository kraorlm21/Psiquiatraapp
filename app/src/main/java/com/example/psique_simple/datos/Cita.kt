package com.example.psique_simple.datos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "citas_agendadas")
data class Cita(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombrePaciente: String,
    val fecha: String, // Formato "YYYY-MM-DD"
    val hora: String, // Formato "HH:MM"
    val motivo: String
)