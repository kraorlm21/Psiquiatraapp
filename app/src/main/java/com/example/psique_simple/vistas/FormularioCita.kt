package com.example.psique_simple.vistas

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.psique_simple.logica.CitasViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioCita(
    logica: CitasViewModel,
    onVolver: () -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // --- CONFIGURACIÓN DEL CALENDARIO (DatePicker) ---
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            // Formateamos a YYYY-MM-DD (Sumamos 1 al mes porque empieza en 0)
            val fechaFormateada = "$year-${(month + 1).toString().padStart(2, '0')}-${dayOfMonth.toString().padStart(2, '0')}"
            logica.onFechaChange(fechaFormateada)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    // Opcional: Limitar para que no elijan fechas pasadas
    datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000

    // --- CONFIGURACIÓN DEL RELOJ (TimePicker) ---
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            // Formateamos a HH:MM
            val horaFormateada = "${hourOfDay.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
            logica.onHoraChange(horaFormateada)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true // true = Formato 24 horas
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agendar Nueva Cita") },
                navigationIcon = {
                    IconButton(onClick = {
                        logica.limpiarFormulario()
                        onVolver()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                "Completa los datos para tu cita",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )

            // 1. Campo Nombre (Texto normal)
            CampoTextoNormal(
                valor = logica.formNombre,
                onValueChange = { logica.onNombreChange(it) },
                label = "Tu Nombre Completo",
                esError = logica.formNombreError,
                errorMsg = "El nombre es obligatorio"
            )

            // 2. Campo Fecha (Con Selector)
            CampoSelector(
                valor = logica.formFecha,
                label = "Fecha de la Cita",
                icono = Icons.Default.DateRange,
                esError = logica.formFechaError,
                errorMsg = "La fecha es obligatoria",
                onClick = { datePickerDialog.show() }
            )

            // 3. Campo Hora (Con Selector)
            CampoSelector(
                valor = logica.formHora,
                label = "Hora de la Cita",
                icono = Icons.Default.DateRange,
                esError = logica.formHoraError,
                errorMsg = "La hora es obligatoria",
                onClick = { timePickerDialog.show() }
            )

            // 4. Motivo (Texto normal)
            OutlinedTextField(
                value = logica.formMotivo,
                onValueChange = { logica.onMotivoChange(it) },
                label = { Text("Motivo de consulta (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { logica.guardarCita(onGuardado = onVolver) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("CONFIRMAR CITA")
            }
        }
    }
}

// --- COMPONENTES AUXILIARES ---

@Composable
fun CampoTextoNormal(
    valor: String,
    onValueChange: (String) -> Unit,
    label: String,
    esError: Boolean,
    errorMsg: String
) {
    Column {
        OutlinedTextField(
            value = valor,
            onValueChange = onValueChange,
            label = { Text(label) },
            isError = esError,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if (esError) Icon(Icons.Filled.Warning, "Error", tint = MaterialTheme.colorScheme.error)
            }
        )
        if (esError) {
            Text(
                text = errorMsg,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun CampoSelector(
    valor: String,
    label: String,
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    esError: Boolean,
    errorMsg: String,
    onClick: () -> Unit
) {
    Column {
        OutlinedTextField(
            value = valor,
            onValueChange = { }, // No permitimos escribir manualmente
            label = { Text(label) },
            readOnly = true, // Importante: solo lectura
            isError = esError,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }, // Clic en todo el campo
            trailingIcon = {
                Icon(
                    imageVector = if (esError) Icons.Filled.Warning else icono,
                    contentDescription = null,
                    tint = if (esError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            // Truco para capturar el click incluso si tocan el texto
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                onClick()
                            }
                        }
                    }
                }
        )
        if (esError) {
            Text(
                text = errorMsg,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}