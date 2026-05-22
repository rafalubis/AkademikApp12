package com.app.akademikapp.receiver
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
class ConnectivityReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        if (
            intent.action ==
            Intent.ACTION_AIRPLANE_MODE_CHANGED
        ) {
            val isAirplaneModeOn =
                intent.getBooleanExtra("state", false)
            if (isAirplaneModeOn) {
                Toast.makeText(
                    context,
                    "Airplane mode aktif",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "Airplane mode nonaktif",
                    Toast.LENGTH_SHORT
                ).show()
            }
            return
        }
        val connectivityManager =
            context.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
        val networkInfo =
            connectivityManager.activeNetworkInfo
        val isConnected =
            networkInfo != null && networkInfo.isConnected
        if (isConnected) {
            Toast.makeText(
                context,
                "Internet terhubung",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                context,
                "Internet terputus",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}