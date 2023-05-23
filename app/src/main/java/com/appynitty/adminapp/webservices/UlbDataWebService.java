package com.appynitty.adminapp.webservices;

import com.appynitty.adminapp.models.SpecificUlbDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UlbDataWebService {
    @GET("api/Supervisor/SelectedUlb")
    Call<SpecificUlbDTO> getUlbData(@Header("Content-Type") String content_type,
                                    @Header("EmpType") String EmpType,
                                    @Header("userId") String userId,
                                    @Header("appId") String appId);
}
