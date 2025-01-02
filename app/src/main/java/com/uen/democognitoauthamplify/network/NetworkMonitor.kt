package com.uen.democognitoauthamplify.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import com.amplifyframework.core.Amplify
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _isConnected.value = true
            Log.i("NetworkMonitor", "Conexión disponible.")
        }

        override fun onLost(network: Network) {
            _isConnected.value = false
            Log.w("NetworkMonitor", "Conexión perdida.")
        }

        override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
            val hasInternet = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            _isConnected.value = hasInternet
            if (hasInternet) {
                Log.i("NetworkMonitor", "Capacidades de red cambiadas: conexión activa.")
            }
        }
    }

    fun startMonitoring() {
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
        Log.i("NetworkMonitor", "Monitor de red iniciado.")
    }

    fun stopMonitoring() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
        Log.i("NetworkMonitor", "Monitor de red detenido.")
    }
}
