package com.josuerdx.gestiglove.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.josuerdx.gestiglove.data.model.UserCredentials

/**
 * DAO para manejar las credenciales de usuario.
 */
@Dao
interface UserCredentialsDao {

    /**
     * Inserta o actualiza las credenciales del usuario.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCredentials(credentials: UserCredentials)

    /**
     * Obtiene las credenciales del usuario por correo.
     */
    @Query("SELECT * FROM user_credentials WHERE email = :email")
    suspend fun getCredentialsByEmail(email: String): UserCredentials?
}