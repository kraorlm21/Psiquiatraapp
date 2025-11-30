package com.example.psique_simple.datos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cita::class], version = 1, exportSchema = false)
abstract class BaseDatos : RoomDatabase() {

    abstract fun citasDao(): CitasDao

    companion object {
        @Volatile
        private var INSTANCIA: BaseDatos? = null

        fun obtener(context: Context): BaseDatos {
            return INSTANCIA ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDatos::class.java,
                    "psique_simple_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCIA = instancia
                instancia
            }
        }
    }
}