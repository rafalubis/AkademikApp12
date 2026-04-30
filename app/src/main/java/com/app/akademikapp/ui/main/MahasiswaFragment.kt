package com.app.akademikapp.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.akademikapp.MainActivity
import com.app.akademikapp.R
import com.app.akademikapp.data.model.MenuAkademik
import com.app.akademikapp.databinding.FragmentMahasiswaBinding
import com.app.akademikapp.ui.adapter.MenuAkademikAdapter

class MahasiswaFragment : Fragment(R.layout.fragment_mahasiswa) {

    private var _binding: FragmentMahasiswaBinding? = null
    private val binding get() = _binding!!

    private lateinit var menuAdapter: MenuAkademikAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMahasiswaBinding.bind(view)

        setupRecyclerView()
        setupActions()
    }

    private fun setupRecyclerView() {

        val menuList = listOf(

            MenuAkademik(
                title = "Profil Mahasiswa",
                description = "Menampilkan identitas dan data akademik dasar mahasiswa."
            ),

            MenuAkademik(
                title = "Jadwal Kuliah",
                description = "Melihat daftar jadwal perkuliahan yang sedang aktif."
            ),

            MenuAkademik(
                title = "Nilai Akademik",
                description = "Menampilkan hasil studi dan nilai setiap mata kuliah."
            ),

            MenuAkademik(
                title = "Kartu Rencana Studi",
                description = "Melihat dan mengelola mata kuliah yang diambil."
            ),

            MenuAkademik(
                title = "Presensi Perkuliahan",
                description = "Melihat riwayat kehadiran dalam kegiatan perkuliahan."
            ),

            MenuAkademik(
                title = "Informasi Tagihan",
                description = "Menampilkan rincian pembayaran dan status tagihan akademik."
            )
        )

        menuAdapter = MenuAkademikAdapter(menuList) { selectedMenu ->

            Toast.makeText(
                requireContext(),
                "Anda memilih menu: ${selectedMenu.title}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.rvMenuAkademik.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = menuAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupActions() {

        binding.btnKembaliHomeMahasiswa.setOnClickListener {
            (activity as? MainActivity)
                ?.supportFragmentManager
                ?.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}