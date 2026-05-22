package com.app.akademikapp.receiver
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
class MahasiswaReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        val namaMahasiswa =
            intent.getStringExtra("nama_mahasiswa")
        Toast.makeText(
            context,
            "Mahasiswa berhasil ditambahkan: $namaMahasiswa",
            Toast.LENGTH_LONG
        ).show()
    }
}