package com.anadolstudio.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import androidx.annotation.RequiresPermission

object ConnectionUtils {

    /**
     * Detects if some network connected.
     *
     * @param context context
     * @return true if network connected, false otherwise.
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?: return false

        return with(networkCapabilities) {
            hasTransport(TRANSPORT_CELLULAR) || hasTransport(TRANSPORT_WIFI)
        }
    }

}
