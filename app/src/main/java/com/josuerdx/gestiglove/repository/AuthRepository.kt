package com.josuerdx.gestiglove.repository

import kotlinx.coroutines.delay

/**
 * Simula las operaciones de autenticación.
 */
class AuthRepository {

    /**
     * Simula el inicio de sesión validando un correo y contraseña ficticios.
     * @param email Correo electrónico ingresado por el usuario.
     * @param password Contraseña ingresada por el usuario.
     * @return Verdadero si las credenciales son correctas; falso en caso contrario.
     */
    suspend fun login(email: String, password: String): Boolean {
        delay(1000)
        return email == "rdx123456@gmail.com" && password == "123456"
    }

    /**
     * Simula el registro validando formato de correo y longitud de contraseña.
     * @param name Nombre completo del usuario.
     * @param email Correo electrónico ingresado por el usuario.
     * @param password Contraseña ingresada por el usuario.
     * @return Verdadero si los datos cumplen las validaciones; falso en caso contrario.
     */
    suspend fun register(name: String, email: String, password: String): Boolean {
        delay(1000)
        return email.contains("@") && password.length >= 6
    }
}