package com.example.psique_simple.logica

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.psique_simple.api.RetrofitClient
import androidx.lifecycle.viewModelScope
import com.example.psique_simple.datos.Cita
import com.example.psique_simple.datos.Repositorio
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class CitasViewModel(private val repositorio: Repositorio) : ViewModel() {

    // 1. Nueva forma de manejar la lista (lo controlamos nosotros)
    private val _listaCitas = MutableStateFlow<List<Cita>>(emptyList())
    val listaCitas: StateFlow<List<Cita>> = _listaCitas.asStateFlow()

    init {
        // Al iniciar, cargamos los datos desde la red
        cargarDatos()
    }

    // Función para recargar los datos desde el repositorio (red)
    fun cargarDatos() {
        viewModelScope.launch {
            repositorio.todasLasCitas.collect { citas ->
                _listaCitas.value = citas // Actualiza el StateFlow
            }
        }
    }


    var estadoClima by mutableStateOf("Cargando clima...")
        private set

    // Icono simple (emoji) para acompañar el texto
    var iconoClima by mutableStateOf("🌤️")
        private set

    init {
        cargarDatos() // Cargar citas
        cargarClima() // Cargar clima (NUEVO)
    }

    fun cargarClima() {
        viewModelScope.launch {
            try {
                val respuesta = RetrofitClient.climaApi.obtenerClimaSantiago()
                val temp = respuesta.current_weather.temperature
                val codigo = respuesta.current_weather.weathercode

                // Traducir código WMO a texto
                val (descripcion, icono) = interpretarCodigoClima(codigo)

                estadoClima = "Santiago: $temp°C - $descripcion"
                iconoClima = icono
            } catch (e: Exception) {
                estadoClima = "Sin información del clima"
                e.printStackTrace()
            }
        }
    }

    // Función auxiliar para traducir códigos del clima
    private fun interpretarCodigoClima(codigo: Int): Pair<String, String> {
        return when (codigo) {
            0 -> "Despejado" to "☀️"
            1, 2, 3 -> "Nublado" to "☁️"
            45, 48 -> "Niebla" to "🌫️"
            51, 53, 55 -> "Llovizna" to "🌦️"
            61, 63, 65 -> "Lluvia" to "🌧️"
            71, 73, 75 -> "Nieve" to "❄️"
            95, 96, 99 -> "Tormenta" to "⛈️"
            else -> "Variable" to "🌤️"
        }
    }

    var formNombre by mutableStateOf("")
    var formFecha by mutableStateOf("")
    var formHora by mutableStateOf("")
    var formMotivo by mutableStateOf("")

    var formNombreError by mutableStateOf(false)
    var formFechaError by mutableStateOf(false)
    var formHoraError by mutableStateOf(false)

    fun onNombreChange(v: String) { formNombre = v; formNombreError = v.isBlank() }
    fun onFechaChange(v: String) { formFecha = v; formFechaError = !esFechaValida(v) }
    fun onHoraChange(v: String) { formHora = v; formHoraError = !esHoraValida(v) }
    fun onMotivoChange(v: String) { formMotivo = v }

    private fun esFechaValida(fecha: String): Boolean {
        val regex = Regex("^\\d{4}-\\d{2}-\\d{2}$")
        if (!regex.matches(fecha)) return false
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            sdf.isLenient = false
            sdf.parse(fecha)
            true
        } catch (e: Exception) { false }
    }

    private fun esHoraValida(hora: String): Boolean {
        val regex = Regex("^([01]\\d|2[0-3]):([0-5]\\d)$")
        return regex.matches(hora)
    }

    fun guardarCita(onGuardado: () -> Unit) {
        onNombreChange(formNombre)
        onFechaChange(formFecha)
        onHoraChange(formHora)

        val hayErrores = formNombreError || formFechaError || formHoraError

        if (!hayErrores) {
            val nuevaCita = Cita(
                nombrePaciente = formNombre,
                fecha = formFecha,
                hora = formHora,
                motivo = formMotivo
            )
            viewModelScope.launch {
                repositorio.insertarCita(nuevaCita)
                // Después de insertar, recargamos la lista desde la red
                cargarDatos()
                limpiarFormulario()
                onGuardado()
            }
        }
    }

    fun borrarCita(cita: Cita) {
        viewModelScope.launch {
            repositorio.borrarCita(cita)
            // Después de borrar, recargamos la lista desde la red
            cargarDatos()
        }
    }

    fun limpiarFormulario() {
        formNombre = ""
        formFecha = ""
        formHora = ""
        formMotivo = ""
    }
}