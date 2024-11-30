package com.josuerdx.gestiglove

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseApp
import com.josuerdx.gestiglove.repository.AuthRepository
import com.josuerdx.gestiglove.utils.network.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Clase personalizada para la aplicación que inicializa configuraciones globales.
 * Sincroniza usuarios pendientes con Firebase si hay conexión a Internet.
 */
class GestiGloveApp : Application() {

    private lateinit var networkMonitor: NetworkMonitor
    val isOnline = MutableLiveData(false)

    override fun onCreate() {
        super.onCreate()

        // Inicialización de Firebase
        FirebaseApp.initializeApp(this)

        // Inicializar monitor de red
        networkMonitor = NetworkMonitor(this)
        networkMonitor.observeForever { isConnected ->
            isOnline.postValue(isConnected)
            if (isConnected) {
                syncPendingUsers()
            }
        }
    }

    private fun syncPendingUsers() {
        val authRepository = AuthRepository(this)
        CoroutineScope(Dispatchers.IO).launch {
            authRepository.syncPendingUsers()
        }
    }
}