package com.app.akademikapp.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.akademikapp.MainActivity
import com.app.akademikapp.R
import com.app.akademikapp.data.model.ApiResponse
import com.app.akademikapp.data.model.Mahasiswa
import com.app.akademikapp.data.remote.ApiClient
import com.app.akademikapp.databinding.FragmentDataMahasiswaApiBinding
import com.app.akademikapp.ui.adapter.MahasiswaApiAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataMahasiswaApiFragment :
    Fragment(R.layout.fragment_data_mahasiswa_api) {

    private var _binding: FragmentDataMahasiswaApiBinding? = null
    private val binding get() = _binding!!

    private lateinit var mahasiswaAdapter: MahasiswaApiAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDataMahasiswaApiBinding.bind(view)

        setupRecyclerView()
        setupActions()
        loadMahasiswa()
    }

    override fun onResume() {
        super.onResume()

        if (_binding != null) {
            loadMahasiswa()
        }
    }

    /* =====================================
       Setup RecyclerView
    ===================================== */
    private fun setupRecyclerView() {

        mahasiswaAdapter = MahasiswaApiAdapter(
            mutableListOf()
        ) { mahasiswa ->
            deleteMahasiswa(mahasiswa.id)
        }

        binding.rvMahasiswa.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mahasiswaAdapter
            setHasFixedSize(true)
        }
    }

    /* =====================================
       Setup Button Actions
    ===================================== */
    private fun setupActions() {

        binding.btnTambahMahasiswa.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(
                TambahMahasiswaApiFragment()
            )
        }

        binding.btnKembaliDataMahasiswa.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    /* =====================================
       Load Data Mahasiswa
    ===================================== */
    private fun loadMahasiswa() {

        ApiClient.apiService
            .getMahasiswa()
            .enqueue(object : Callback<ApiResponse<List<Mahasiswa>>> {

                override fun onResponse(
                    call: Call<ApiResponse<List<Mahasiswa>>>,
                    response: Response<ApiResponse<List<Mahasiswa>>>
                ) {

                    if (response.isSuccessful) {

                        val data = response.body()?.data ?: emptyList()

                        mahasiswaAdapter.updateData(data)

                        binding.tvEmptyMahasiswa.isVisible = data.isEmpty()

                        binding.rvMahasiswa.isVisible = data.isNotEmpty()

                    } else {

                        Toast.makeText(
                            requireContext(),
                            "Gagal mengambil data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<ApiResponse<List<Mahasiswa>>>,
                    t: Throwable
                ) {

                    Toast.makeText(
                        requireContext(),
                        "Error: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    /* =====================================
       Delete Mahasiswa
    ===================================== */
    private fun deleteMahasiswa(id: Int) {

        ApiClient.apiService
            .deleteMahasiswa(id)
            .enqueue(object : Callback<ApiResponse<Any>> {

                override fun onResponse(
                    call: Call<ApiResponse<Any>>,
                    response: Response<ApiResponse<Any>>
                ) {

                    if (response.isSuccessful) {

                        Toast.makeText(
                            requireContext(),
                            "Data berhasil dihapus",
                            Toast.LENGTH_SHORT
                        ).show()

                        loadMahasiswa()

                    } else {

                        Toast.makeText(
                            requireContext(),
                            "Gagal menghapus data",
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

        binding.rvMahasiswa.adapter = null
        _binding = null
    }
}