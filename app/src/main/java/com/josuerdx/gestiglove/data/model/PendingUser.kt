package com.josuerdx.gestiglove.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Modelo para guardar registros cuando no hay conexión a Internet.
 */

@Entity(tableName = "pending_users")
data class PendingUser(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val password: String
)
