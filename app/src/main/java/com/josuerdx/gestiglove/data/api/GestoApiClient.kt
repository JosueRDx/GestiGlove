package com.josuerdx.gestiglove.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Cliente Retrofit para gestionar la API de gestos.
 */
object GestoApiClient {
    private const val BASE_URL = "http://192.168.152.35:8000/api/"

    val service: GestoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GestoApiService::class.java)
    }
}