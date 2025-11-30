package com.example.psique_simple.logica

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.psique_simple.datos.Repositorio
import java.lang.IllegalArgumentException

class CitasViewModelFactory(
    private val repositorio: Repositorio
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CitasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CitasViewModel(repositorio) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}