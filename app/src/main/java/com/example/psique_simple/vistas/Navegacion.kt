package com.example.psique_simple.vistas

import androidx.compose.animation.* // Importa todo lo necesario para animaciones
import androidx.compose.animation.core.tween // Importa 'tween' para definir la duración
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.psique_simple.logica.CitasViewModel

// Composable que gestiona la navegación entre las pantallas de la app.
@Composable
fun Navegacion(logica: CitasViewModel) {
    // Controlador de navegación que mantiene el estado de las pantallas.
    val navegador = rememberNavController()
    // Duración de las animaciones de transición en milisegundos.
    val duracionAnim = 700

    // Contenedor principal para la navegación. Define las rutas y sus pantallas.
    NavHost(navController = navegador, startDestination = "principal") {

        // Define la ruta "principal" y su pantalla asociada (PantallaPrincipal).
        composable(
            route = "principal",
            // Animación al entrar a esta pantalla (cuando se vuelve de otra).
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(duracionAnim)) +
                        fadeIn(tween(duracionAnim))
            },
            // Animación al salir de esta pantalla (cuando se navega a otra).
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(duracionAnim)) +
                        fadeOut(tween(duracionAnim))
            }
        ) {
            // Contenido de la pantalla principal.
            PantallaPrincipal(
                logica = logica, // Pasa el ViewModel.
                onIrAFormulario = { // Lambda que se ejecuta al pulsar el botón '+'.
                    navegador.navigate("formulario") // Navega a la ruta "formulario".
                }
            )
        }

        // Define la ruta "formulario" y su pantalla asociada (FormularioCita).
        composable(
            route = "formulario",
            // Animación al entrar a esta pantalla.
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(duracionAnim)) +
                        fadeIn(tween(duracionAnim))
            },
            // Animación al salir de esta pantalla (cuando se presiona "atrás").
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(duracionAnim)) +
                        fadeOut(tween(duracionAnim))
            }
            // Podrías añadir popEnterTransition y popExitTransition si quieres
            // animaciones diferentes al usar el botón "atrás" del sistema.
        ) {
            // Contenido de la pantalla del formulario.
            FormularioCita(
                logica = logica, // Pasa el ViewModel.
                onVolver = { // Lambda que se ejecuta al pulsar el botón "atrás" de la AppBar.
                    navegador.popBackStack() // Vuelve a la pantalla anterior en la pila.
                }
            )
        }
    }
}