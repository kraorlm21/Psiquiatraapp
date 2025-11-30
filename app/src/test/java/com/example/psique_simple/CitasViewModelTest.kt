package com.example.psique_simple.logica

import com.example.psique_simple.api.ClimaApi
import com.example.psique_simple.api.ClimaResponse
import com.example.psique_simple.api.CurrentWeather
import com.example.psique_simple.api.RetrofitClient
import com.example.psique_simple.datos.Cita
import com.example.psique_simple.datos.Repositorio
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CitasViewModelTest {

    // 1. Mocks: Objetos falsos
    @RelaxedMockK
    private lateinit var repositorio: Repositorio

    @RelaxedMockK
    private lateinit var climaApi: ClimaApi

    private lateinit var viewModel: CitasViewModel

    // Dispatcher para corrutinas en test
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        // IMPORTANTE: Simulamos el Singleton de RetrofitClient para la API del clima
        mockkObject(RetrofitClient)
        every { RetrofitClient.climaApi } returns climaApi

        // Simulamos que el repositorio devuelve un Flow vacío por defecto para evitar errores
        every { repositorio.todasLasCitas } returns MutableStateFlow(emptyList())

        // Inicializamos el ViewModel
        viewModel = CitasViewModel(repositorio)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    // --- PRUEBA 1: Verificar que guardarCita llama al repositorio ---
    @Test
    fun `guardarCita debe llamar a insertarCita en el repositorio si los datos son validos`() = runTest {
        // GIVEN: Datos válidos en el formulario
        viewModel.onNombreChange("Juan Perez")
        viewModel.onFechaChange("2025-12-01") // Formato correcto YYYY-MM-DD
        viewModel.onHoraChange("15:30")       // Formato correcto HH:MM
        viewModel.onMotivoChange("Control mensual")

        // WHEN: Guardamos
        viewModel.guardarCita {}

        // THEN: Verificamos que se llamó a repositorio.insertarCita con los datos correctos
        coVerify {
            repositorio.insertarCita(match {
                it.nombrePaciente == "Juan Perez" &&
                        it.fecha == "2025-12-01" &&
                        it.motivo == "Control mensual"
            })
        }
    }

    // --- PRUEBA 2: Verificar validación de errores (Fecha incorrecta) ---
    @Test
    fun `guardarCita NO debe llamar al repositorio si la fecha es invalida`() = runTest {
        // GIVEN: Fecha con formato erróneo
        viewModel.onNombreChange("Ana")
        viewModel.onFechaChange("01-12-2025") // Incorrecto, debe ser YYYY-MM-DD
        viewModel.onHoraChange("10:00")

        // WHEN: Intentamos guardar
        viewModel.guardarCita {}

        // THEN: El repositorio NUNCA debió ser llamado
        coVerify(exactly = 0) { repositorio.insertarCita(any()) }

        // Y debe haber error en el estado
        assertTrue(viewModel.formFechaError)
    }

    // --- PRUEBA 3: Verificar carga de Clima ---
    @Test
    fun `cargarClima debe actualizar el estado con la temperatura de Santiago`() = runTest {
        // GIVEN: Simulamos respuesta de Open-Meteo (Código 0 = Despejado)
        val respuestaFalsa = ClimaResponse(
            current_weather = CurrentWeather(temperature = 28.5, weathercode = 0, windspeed = 10.0)
        )
        coEvery { climaApi.obtenerClimaSantiago() } returns respuestaFalsa

        // WHEN: Llamamos a cargar clima
        viewModel.cargarClima()

        // THEN: El texto debe contener la temperatura y la descripción
        assertTrue(viewModel.estadoClima.contains("28.5°C"))
        assertTrue(viewModel.estadoClima.contains("Despejado"))
        assertEquals("☀️", viewModel.iconoClima)
    }
}