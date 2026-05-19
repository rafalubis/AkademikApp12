package com.app.akademikapp.ui.main
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.app.akademikapp.MainActivity
import com.app.akademikapp.R
import com.app.akademikapp.databinding.FragmentAdminBinding
class AdminFragment : Fragment(R.layout.fragment_admin) {
    private var _binding: FragmentAdminBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAdminBinding.bind(view)
        binding.btnKelolaMahasiswa.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(
                DataMahasiswaApiFragment()
            )
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}