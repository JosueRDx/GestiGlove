package com.josuerdx.gestiglove.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import com.josuerdx.gestiglove.data.model.PendingUser

/**
 * DAO para insertar, listar y eliminar usuarios pendientes de sincronización.
 */
@Dao
interface PendingUserDao {

    /**
     * Inserta un nuevo usuario pendiente en la base de datos.
     */
    @Insert
    suspend fun insertUser(user: PendingUser)

    /**
     * Obtiene todos los usuarios pendientes de sincronización.
     */
    @Query("SELECT * FROM pending_users")
    suspend fun getAllPendingUsers(): List<PendingUser>

    /**
     * Elimina un usuario pendiente específico de la base de datos.
     */
    @Delete
    suspend fun deleteUser(user: PendingUser)

    /**
     * Borra todos los usuarios pendientes de la base de datos.
     */
    @Query("DELETE FROM pending_users")
    suspend fun deleteAllPendingUsers()
}