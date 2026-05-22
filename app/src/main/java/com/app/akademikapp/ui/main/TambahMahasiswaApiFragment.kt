package com.app.akademikapp.ui.main
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.akademikapp.R
import com.app.akademikapp.data.model.ApiResponse
import com.app.akademikapp.data.model.Mahasiswa
import com.app.akademikapp.data.remote.ApiClient
import com.app.akademikapp.databinding.FragmentTambahMahasiswaApiBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import com.app.akademikapp.utils.BroadcastActions
class TambahMahasiswaApiFragment :
    Fragment(R.layout.fragment_tambah_mahasiswa_api) {
    private var _binding: FragmentTambahMahasiswaApiBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTambahMahasiswaApiBinding.bind(view)
        binding.btnSimpanMahasiswa.setOnClickListener {
            saveMahasiswa()
        }
        binding.btnKembaliTambahMahasiswa.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
    private fun saveMahasiswa() {
        val nim = binding.edtNim.text.toString().trim()
        val nama = binding.edtNama.text.toString().trim()
        val prodi = binding.edtProdi.text.toString().trim()
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
            id = 0,
            nim = nim,
            nama = nama,
            prodi = prodi,
            semester = semester,
            email = email
        )
        ApiClient.apiService.tambahMahasiswa(mahasiswa)
            .enqueue(object : Callback<ApiResponse<Any>> {
                override fun onResponse(
                    call: Call<ApiResponse<Any>>,
                    response: Response<ApiResponse<Any>>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Mahasiswa berhasil ditambahkan",
                            Toast.LENGTH_SHORT
                        ).show()
                        parentFragmentManager.popBackStack()
                        val broadcastIntent = Intent(
                            BroadcastActions.ACTION_MAHASISWA_DITAMBAH
                        )
                        broadcastIntent.putExtra(
                            "nama_mahasiswa",
                            nama
                        )
                        requireContext().sendBroadcast(
                            broadcastIntent
                        )
                        parentFragmentManager.popBackStack()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Gagal menambah mahasiswa",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun onFailure(
                    call: Call<ApiResponse<Any>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}