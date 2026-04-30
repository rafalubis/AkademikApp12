package com.app.akademikapp
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.akademikapp.databinding.ActivityMainBinding
import com.app.akademikapp.ui.main.HomeFragment
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment(), false)
        }
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {


        val transaction = supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }
}