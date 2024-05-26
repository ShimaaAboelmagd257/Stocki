package com.example.stocki.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Handler
import android.os.Looper

/*
class NetworkStateManager(private val context: Context) : ConnectivityManager.NetworkCallback() {
    private val networkStateListeners: MutableList<NetworkStateListener> = ArrayList()
    private val handler: Handler = Handler(Looper.getMainLooper())
    private val connectivityManager: ConnectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    init {
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    override fun onAvailable(network: Network) {
        handler.post {
            notifyNetworkAvailable()
        }
    }

    override fun onLost(network: Network) {
        if (!isConnected()) {
            handler.post {
                notifyNetworkUnavailable()
            }
        }
    }

    private fun notifyNetworkAvailable() {
        networkStateListeners.forEach { it.onNetworkAvailable() }
    }

    private fun notifyNetworkUnavailable() {
        networkStateListeners.forEach { it.onNetworkUnavailable() }
    }

    fun isConnected(): Boolean {
        return connectivityManager.activeNetwork != null
    }

    fun addNetworkStateListener(listener: NetworkStateListener) {
        networkStateListeners.add(listener)
    }

    fun removeNetworkStateListener(listener: NetworkStateListener) {
        networkStateListeners.remove(listener)
    }
}
*/
interface NetworkStateListener {
    fun onNetworkStateChanged(networkState: NetworkState)
}
sealed class NetworkState {
    object Available : NetworkState()
    object Unavailable : NetworkState()
}
object NetworkStateManager : ConnectivityManager.NetworkCallback() {
    private val networkStateListeners: MutableList<NetworkStateListener> = ArrayList()
    private val handler: Handler = Handler(Looper.getMainLooper())
    private lateinit var connectivityManager: ConnectivityManager

    fun initialize(context: Context) {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    override fun onAvailable(network: Network) {
        handler.post {
            notifyNetworkStateChanged(NetworkState.Available)
        }
    }

    override fun onLost(network: Network) {
        if (!isConnected()) {
            handler.post {
                notifyNetworkStateChanged(NetworkState.Unavailable)
            }
        }
    }

    private fun notifyNetworkStateChanged(networkState: NetworkState) {
        networkStateListeners.forEach { it.onNetworkStateChanged(networkState) }
    }

    fun isConnected(): Boolean {
        return connectivityManager.activeNetwork != null
    }

    fun addNetworkStateListener(listener: NetworkStateListener) {
        networkStateListeners.add(listener)
    }

    fun removeNetworkStateListener(listener: NetworkStateListener) {
        networkStateListeners.remove(listener)
    }
}
