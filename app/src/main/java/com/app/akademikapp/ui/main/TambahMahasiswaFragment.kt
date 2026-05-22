package com.app.akademikapp.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.akademikapp.R
import com.app.akademikapp.data.local.MahasiswaDbHelper
import com.app.akademikapp.data.model.Mahasiswa
import com.app.akademikapp.databinding.FragmentTambahMahasiswaBinding

class TambahMahasiswaFragment :
    Fragment(R.layout.fragment_tambah_mahasiswa) {

    private var _binding:
            FragmentTambahMahasiswaBinding? = null

    private val binding get() = _binding!!

    private lateinit var dbHelper: MahasiswaDbHelper

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding =
            FragmentTambahMahasiswaBinding.bind(view)

        dbHelper =
            MahasiswaDbHelper(requireContext())

        setupActions()
    }

    /* =====================================
       Setup Actions
    ===================================== */

    private fun setupActions() {

        binding.btnSimpanMahasiswa.setOnClickListener {
            saveMahasiswa()
        }

        binding.btnKembaliTambahMahasiswa.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    /* =====================================
       Save Mahasiswa
    ===================================== */

    private fun saveMahasiswa() {

        val nim =
            binding.edtNim.text.toString().trim()

        val nama =
            binding.edtNama.text.toString().trim()

        val prodi =
            binding.edtProdi.text.toString().trim()

        val semesterText =
            binding.edtSemester.text.toString().trim()

        val email =
            binding.edtEmail.text.toString().trim()

        if (
            nim.isEmpty() ||
            nama.isEmpty() ||
            prodi.isEmpty() ||
            semesterText.isEmpty() ||
            email.isEmpty()
        ) {

            Toast.makeText(
                requireContext(),
                "Semua field wajib diisi",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val semester = semesterText.toIntOrNull()

        if (semester == null || semester <= 0) {

            Toast.makeText(
                requireContext(),
                "Semester tidak valid",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val mahasiswa = Mahasiswa(
            nim = nim,
            nama = nama,
            prodi = prodi,
            semester = semester,
            email = email
        )

        val success =
            dbHelper.insertMahasiswa(mahasiswa)

        if (success) {

            Toast.makeText(
                requireContext(),
                "Data mahasiswa berhasil disimpan",
                Toast.LENGTH_SHORT
            ).show()

            parentFragmentManager.popBackStack()

        } else {

            Toast.makeText(
                requireContext(),
                "Gagal menyimpan. NIM mungkin sudah ada.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}