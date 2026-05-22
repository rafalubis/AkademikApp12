package com.app.akademikapp
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.app.akademikapp.databinding.ActivityMainBinding
import com.app.akademikapp.receiver.MahasiswaReceiver
import com.app.akademikapp.ui.main.HomeFragment
import com.app.akademikapp.utils.BroadcastActions
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mahasiswaReceiver: MahasiswaReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerMahasiswaReceiver()
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment(), false)
        }
    }
    private fun registerMahasiswaReceiver() {
        mahasiswaReceiver = MahasiswaReceiver()
        val filter = IntentFilter(
            BroadcastActions.ACTION_MAHASISWA_DITAMBAH
        )
        ContextCompat.registerReceiver(
            this,
            mahasiswaReceiver,
            filter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }
    fun replaceFragment(
        fragment: Fragment,
        addToBackStack: Boolean = true
    ) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mahasiswaReceiver)
    }
}