package com.project.husni.uasptm.api;

import com.project.husni.uasptm.model.ModelData_load;
import com.project.husni.uasptm.model.ResponsModel;
import com.project.husni.uasptm.model.getdatamark;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiRequestBiodata {
    @Multipart
    @POST("tambah_data.php")
    Call<ResponsModel> sendBiodata(@Part MultipartBody.Part nbi,
                                   @Part MultipartBody.Part nama,
                                   @Part MultipartBody.Part alamat,
                                   @Part MultipartBody.Part tanggal_lahir,
                                   @Part MultipartBody.Part fakultas,
                                   @Part MultipartBody.Part jurusan,
                                   @Part MultipartBody.Part jenis_kelamin,
                                   @Part MultipartBody.Part hobi,
                                   @Part MultipartBody.Part kewarganegaraan,
                                   @Part MultipartBody.Part image,
                                   @Part MultipartBody.Part latitude,
                                   @Part MultipartBody.Part longitude,
                                   @Part MultipartBody.Part tanggal_lulus,
                                   @Part MultipartBody.Part jumlah_semester,
                                   @Part MultipartBody.Part dosen_wali,
                                   @Part MultipartBody.Part lokasi);

    @FormUrlEncoded
    @POST("lihat_data.php")
    Call<ModelData_load> sendNbi(@Field("nbi") String nbi);

    @Multipart
    @POST("edit_data.php")
    Call<ResponsModel> editBiodata(@Part MultipartBody.Part nbi,
                                   @Part MultipartBody.Part nama,
                                   @Part MultipartBody.Part alamat,
                                   @Part MultipartBody.Part tanggal_lahir,
                                   @Part MultipartBody.Part fakultas,
                                   @Part MultipartBody.Part jurusan,
                                   @Part MultipartBody.Part jenis_kelamin,
                                   @Part MultipartBody.Part hobi,
                                   @Part MultipartBody.Part kewarganegaraan,
                                   @Part MultipartBody.Part image,
                                   @Part MultipartBody.Part latitude,
                                   @Part MultipartBody.Part longitude,
                                   @Part MultipartBody.Part tanggal_lulus ,
                                   @Part MultipartBody.Part jumlah_semester,
                                   @Part MultipartBody.Part dosen_wali,
                                   @Part MultipartBody.Part lokasi);

    @FormUrlEncoded
    @POST("hapus_data.php")
    Call<ResponsModel> hapusBiodata(@Field("nbi") String nbi);

    @GET("lihat_marker.php")
    Call<getdatamark> loadMarker();
}
