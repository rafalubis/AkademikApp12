package com.app.akademikapp.data.remote
import com.app.akademikapp.data.model.ApiResponse
import com.app.akademikapp.data.model.Mahasiswa
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
interface ApiService {
    @GET("mahasiswa")
    fun getMahasiswa():
            Call<ApiResponse<List<Mahasiswa>>>
    @POST("mahasiswa")
    fun tambahMahasiswa(
        @Body mahasiswa: Mahasiswa
    ): Call<ApiResponse<Any>>
    @DELETE("mahasiswa/{id}")
    fun deleteMahasiswa(
        @Path("id") id: Int
    ): Call<ApiResponse<Any>>
}
