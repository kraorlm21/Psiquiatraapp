package com.example.psique_simple.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    
    private const val URL_BACKEND = "http://10.0.0.2:8080/"

    val psiquiatriaApi: PsiquiatriaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(URL_BACKEND)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PsiquiatriaApiService::class.java)
    }

    // --- 2. API CLIMA (Open-Meteo) ---
    private const val URL_CLIMA = "https://api.open-meteo.com/"

    val climaApi: ClimaApi by lazy {
        Retrofit.Builder()
            .baseUrl(URL_CLIMA)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ClimaApi::class.java)
    }

}
