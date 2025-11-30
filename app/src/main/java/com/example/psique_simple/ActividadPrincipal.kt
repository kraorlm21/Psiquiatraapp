package com.example.psique_simple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
// 1. FIX THEME IMPORT: Point to your ui.theme package and the correct Theme function name
import com.example.psique_simple.ui.theme.PsiqueSimpleTheme // Or AgendaPsiquiatraTheme if you renamed it
// 2. FIX NAVIGATION IMPORT: Point to your vistas package
import com.example.psique_simple.vistas.Navegacion
import com.example.psique_simple.logica.CitasViewModel
import com.example.psique_simple.logica.CitasViewModelFactory // Corrected Factory name

class ActividadPrincipal : ComponentActivity() {

    private val logica: CitasViewModel by viewModels {
        CitasViewModelFactory(
            (application as Aplicacion).repositorio
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PsiqueSimpleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navegacion(logica = logica)
                }
            }
        }
    }
}