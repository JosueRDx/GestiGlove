package com.josuerdx.gestiglove.data.api

import com.josuerdx.gestiglove.data.model.Gesto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Body

/**
 * Define las rutas de la API para gestionar los gestos.
 */
interface GestoApiService {
    @GET("gestos/")
    suspend fun getGestos(): Response<List<Gesto>>

    @PUT("gestos/{id}/")
    suspend fun updateGesto(@Path("id") id: Int, @Body gesto: Gesto): Response<Gesto>
}