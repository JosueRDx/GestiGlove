package com.josuerdx.gestiglove.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData

/**
 * Clase para monitorear el estado de la conexión a Internet.
 */
class NetworkMonitor(context: Context) : LiveData<Boolean>() {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            postValue(true)
        }

        override fun onLost(network: Network) {
            postValue(false)
        }
    }

    override fun onActive() {
        super.onActive()
        updateConnection()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        }
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun updateConnection() {
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        postValue(
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true ||
                    networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
        )
    }
}