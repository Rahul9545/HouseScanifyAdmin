package com.appynitty.adminapp.webservices;


import com.appynitty.adminapp.models.AddEmpDTO;
import com.appynitty.adminapp.models.AddEmpResult;
import com.appynitty.adminapp.models.EmpDModelDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AddEmpWebService {
    @POST("api/Supervisor/AddHouseScanifyEmployee")
    Call<List<AddEmpResult>> addNewEmpHS(@Header("Content-Type") String content_type,
                                   @Header("appId") String appId,
                                   @Body List<AddEmpDTO> addEmpDTO);

    @POST("api/Supervisor/AddHouseScanifyEmployee")
    Call<List<AddEmpResult>> updateEmployeeDetails(@Header("Content-Type") String content_type,
                                             @Header("appId") String appId,
                                             @Body List<EmpDModelDTO> addEmpDTO);

}
