package com.appynitty.adminapp.webservices;

import com.appynitty.adminapp.models.ReferenceIdDetailsDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface GetReferenceIdDetailsWebService {
    @GET("api/Supervisor/GetAllHDSLDetails")
    Call<List<ReferenceIdDetailsDTO>> getReferenceIdDetails(@Header("appId") String appID, @Header("ReferanceId") String referenceId);
}
