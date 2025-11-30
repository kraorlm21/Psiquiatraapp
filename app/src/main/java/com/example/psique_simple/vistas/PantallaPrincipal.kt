package com.example.psique_simple.vistas

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment // Importante para la alineación del clima
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.psique_simple.datos.Cita
import com.example.psique_simple.logica.CitasViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(
    logica: CitasViewModel,
    onIrAFormulario: () -> Unit
) {
    val listaCitas by logica.listaCitas.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agenda Psiquiátrica") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    // --- RECURSO NATIVO 1: LLAMAR A LA CLÍNICA ---
                    IconButton(onClick = {
                        val numero = "+56912345678"
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:$numero")
                        }
                        context.startActivity(intent)
                    }) {
                        Icon(Icons.Filled.Call, contentDescription = "Llamar a la clínica")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onIrAFormulario) {
                Icon(Icons.Filled.Add, contentDescription = "Agendar Cita")
            }
        }
    ) { padding ->
        // Usamos Column para apilar el Clima y la Lista de Citas
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            // --- NUEVO: Tarjeta del Clima (API Open-Meteo) ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Muestra el icono (emoji)
                    Text(
                        text = logica.iconoClima,
                        style = MaterialTheme.typography.displayMedium
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Clima Actual",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                        // Muestra la temperatura y descripción
                        Text(
                            text = logica.estadoClima,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            // --- Lista de Citas ---
            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 80.dp), // bottom padding para que el FAB no tape el último item
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (listaCitas.isEmpty()) {
                    item {
                        Text(
                            "No tienes citas agendadas.",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                } else {
                    items(listaCitas) { cita ->
                        TarjetaCita(
                            cita = cita,
                            onBorrar = { logica.borrarCita(cita) },
                            onAgregarACalendario = {
                                agregarEventoACalendario(context, cita)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaCita(
    cita: Cita,
    onBorrar: () -> Unit,
    onAgregarACalendario: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                cita.nombrePaciente,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text("Fecha: ${cita.fecha}", style = MaterialTheme.typography.bodyLarge)
            Text("Hora: ${cita.hora}", style = MaterialTheme.typography.bodyLarge)
            if (cita.motivo.isNotBlank()) {
                Text("Motivo: ${cita.motivo}", style = MaterialTheme.typography.bodyMedium)
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // --- RECURSO NATIVO 2: AÑADIR A CALENDARIO ---
                Button(onClick = onAgregarACalendario) {
                    Icon(Icons.Default.DateRange, contentDescription = "Calendario", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Agendar")
                }
                IconButton(onClick = onBorrar) {
                    Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

// --- FUNCIÓN HELPER PARA RECURSO NATIVO (CALENDARIO) ---
private fun agregarEventoACalendario(context: Context, cita: Cita) {
    try {
        val formato = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val fechaHoraStr = "${cita.fecha} ${cita.hora}"
        val fechaInicioMs = formato.parse(fechaHoraStr)?.time ?: System.currentTimeMillis()

        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, fechaInicioMs)
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, fechaInicioMs + 60 * 60 * 1000) // 1 hora de duración
            .putExtra(CalendarContract.Events.TITLE, "Cita Psiquiátrica")
            .putExtra(CalendarContract.Events.DESCRIPTION, "Paciente: ${cita.nombrePaciente}. Motivo: ${cita.motivo}")
            .putExtra(CalendarContract.Events.EVENT_LOCATION, "Consulta Virtual")

        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Error al parsear fecha/hora. Asegúrate de usar HH:MM (24h).", Toast.LENGTH_LONG).show()
    }
}