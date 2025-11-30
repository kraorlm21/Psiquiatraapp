package com.example.psique_simple

import android.app.Application
import com.example.psique_simple.datos.BaseDatos
import com.example.psique_simple.datos.Repositorio

class Aplicacion : Application() {
    val baseDatos by lazy { BaseDatos.obtener(this) }
    val repositorio by lazy { Repositorio(baseDatos.citasDao()) }
}