package com.appynitty.adminapp.webservices;

import com.appynitty.adminapp.models.QREmployeeDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface QREmployeeListWebService {
    @GET("api/Supervisor/QREmployeeList")
    Call<ArrayList<QREmployeeDTO>> getAllQREmployees(@Header("Content-Type") String contentType,
                                                     @Header("EmpType") String EmpType,
                                                     @Header("userId") String toDate,
                                                     @Header("appId") String appId);
}
