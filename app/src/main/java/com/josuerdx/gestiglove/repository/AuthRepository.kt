package com.josuerdx.gestiglove.repository

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.josuerdx.gestiglove.data.database.AppDatabase
import com.josuerdx.gestiglove.data.model.PendingUser
import com.josuerdx.gestiglove.data.model.UserCredentials
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest

class AuthRepository(context: Context) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userCredentialsDao = AppDatabase.getDatabase(context).userCredentialsDao()
    private val pendingUserDao = AppDatabase.getDatabase(context).pendingUserDao()

    /**
     * Hash de contraseña usando SHA-256.
     */
    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    /**
     * Inicia sesión con Firebase y guarda las credenciales localmente.
     */
    suspend fun login(email: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            saveCredentialsOffline(email, password)
            true
        } catch (e: Exception) {
            val cachedUser = userCredentialsDao.getCredentialsByEmail(email)
            val hashedInputPassword = hashPassword(password)
            cachedUser != null && cachedUser.password == hashedInputPassword
        }
    }

    /**
     * Guarda las credenciales localmente.
     */
    private suspend fun saveCredentialsOffline(email: String, password: String) {
        val hashedPassword = hashPassword(password)
        val credentials = UserCredentials(email, hashedPassword)
        userCredentialsDao.insertCredentials(credentials)
    }

    /**
     * Registra un usuario con Firebase y guarda las credenciales localmente.
     */
    suspend fun register(name: String, email: String, password: String): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            saveCredentialsOffline(email, password)
            true
        } catch (e: Exception) {
            val user = PendingUser(name = name, email = email, password = password)
            pendingUserDao.insertUser(user)
            saveCredentialsOffline(email, password)
            false
        }
    }

    /**
     * Verifica si un correo ya está en uso en Firebase o en la DB local.
     */
    suspend fun isEmailInUse(email: String): Boolean {
        return try {
            val existingMethods = auth.fetchSignInMethodsForEmail(email).await()
            if (!existingMethods.signInMethods.isNullOrEmpty()) {
                true
            } else {
                userCredentialsDao.getCredentialsByEmail(email) != null
            }
        } catch (e: Exception) {
            userCredentialsDao.getCredentialsByEmail(email) != null
        }
    }

    /**
     * Sincroniza los usuarios pendientes con Firebase cuando hay conexión a Internet.
     * Después de sincronizarlos exitosamente, los elimina de la base de datos local.
     */
    suspend fun syncPendingUsers() {
        val pendingUsers = pendingUserDao.getAllPendingUsers()
        for (user in pendingUsers) {
            try {
                val existingMethods = auth.fetchSignInMethodsForEmail(user.email).await()
                if (existingMethods.signInMethods.isNullOrEmpty()) {
                    auth.createUserWithEmailAndPassword(user.email, user.password).await()
                    pendingUserDao.deleteUser(user)
                } else {
                    pendingUserDao.deleteUser(user)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}