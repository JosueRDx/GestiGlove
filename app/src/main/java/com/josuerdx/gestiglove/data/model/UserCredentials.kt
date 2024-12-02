    package com.josuerdx.gestiglove.data.model

    import androidx.room.Entity
    import androidx.room.PrimaryKey

    /**
     * Modelo de datos para las credenciales de usuario almacenadas localmente.
     */
    @Entity(tableName = "user_credentials")
    data class UserCredentials(
        @PrimaryKey val email: String,
        val password: String,
        val plainPassword: String
    )
