# Psiquiatraapp
# 🧠 Psiquiatraapp / psique_simple

Una aplicación móvil Android desarrollada con **Kotlin** y **Jetpack Compose** que funciona como una agenda para administrar citas psiquiátricas. El proyecto sigue la arquitectura **MVVM** (Model-View-ViewModel), integra operaciones de red con **Retrofit** para gestionar las citas en un backend y consulta una API externa para mostrar el clima actual de Santiago, Chile.

## ✨ Características Principales

* **Agenda de Citas (CRUD):** Permite listar, agendar y borrar citas. Las operaciones de persistencia se realizan directamente contra un API RESTful de backend.
* **Formulario Reactivo:** Formulario de agendamiento con validación simple de campos obligatorios (nombre, fecha, hora).
* **Integración de Servicios Nativos:**
    * Botón para realizar una llamada telefónica a la clínica.
    * Opción para **añadir la cita al calendario nativo** de Android.
* **Información del Clima:** Muestra la temperatura y la condición meteorológica actual de Santiago, Chile, consumiendo la API de Open-Meteo.
* **Navegación con Animaciones:** Utiliza `Navigation Compose` con transiciones personalizadas para una experiencia fluida entre pantallas.

## 💻 Tecnologías Utilizadas

| Categoría | Tecnología | Detalles |
| :--- | :--- | :--- |
| **Lenguaje** | **Kotlin** (`2.0.21`) | Lenguaje principal de desarrollo. |
| **UI** | **Jetpack Compose** (`2024.09.00`) | Framework moderno y declarativo para construir la interfaz de usuario. |
| **Arquitectura** | **MVVM, Coroutines & Flow** | Uso de `ViewModel`, `CoroutineScope` y `StateFlow` para manejar el estado de la UI y los datos asíncronos. |
| **Persistencia** | **Room** (`2.6.1`) | Base de datos local (configurada para un posible uso offline/caché). |
| **Red** | **Retrofit** (`2.9.0`) & **Gson** | Para la comunicación con APIs REST (Backend de Citas y Open-Meteo). |
| **Testing** | **JUnit, MockK, Coroutines Test** | Implementación de pruebas unitarias para el `ViewModel` y la lógica de negocio. |

## 🛠️ Configuración y Endpoints

El proyecto utiliza dos servicios REST, configurados en `RetrofitClient.kt`.

### Backend de Citas

* **URL Base:** `http://10.0.0.2:8080/`.
    * **⚠️ Importante:** Necesitará reemplazar `10.0.0.2` por la IP real de su servidor local o el host de su emulador/dispositivo. Para emuladores de Android Studio, a menudo se usa `10.0.2.2`.
* **Endpoints clave (en `PsiquiatriaApiService.kt`):**
    * `GET api/citas`: Obtener todas las citas.
    * `POST api/citas`: Agendar una nueva cita.
    * `DELETE api/citas/{id}`: Borrar una cita por ID.

### API del Clima (Open-Meteo)

* **Endpoint:** `GET v1/forecast?latitude=-33.4489&longitude=-70.6693&current_weather=true`.
* Consulta el clima de Santiago (Lat: -33.4489, Long: -70.6693).

## ⚙️ Instalación y Ejecución

1.  **Clonar el Repositorio**.
2.  **Configurar el Backend:** Modifique la URL de la IP en `app/src/main/java/com/example/psique_simple/api/RetrofitClient.kt`.
3.  **Permisos:** La aplicación requiere permisos de `INTERNET`, `READ_CALENDAR` y `WRITE_CALENDAR`.
4.  **Ejecutar:** Abra el proyecto en Android Studio, sincronice Gradle y ejecute la aplicación en un emulador o dispositivo Android (Mínimo SDK 24).

## 🧪 Pruebas Unitarias

El proyecto incluye pruebas unitarias para el `CitasViewModel` utilizando **MockK** para simular dependencias (como el Repositorio y la API del clima) y **Kotlin Coroutines Test** para manejar el flujo asíncrono.

Para ejecutar las pruebas:
```bash
./gradlew testDebugUnitTest
