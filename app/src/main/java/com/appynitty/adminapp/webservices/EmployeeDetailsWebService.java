package com.appynitty.adminapp.webservices;

import com.appynitty.adminapp.models.EmployeeDetailsDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface EmployeeDetailsWebService {

    @GET("api/Supervisor/HouseScanifyDetailsGridRow")
    Call<List<EmployeeDetailsDTO>> getEmployeesDetails(@Header("Content-Type") String contentType,
                                                       @Header("FromDate") String fromDate,
                                                       @Header("Todate") String toDate,
                                                       @Header("appId") String appId,
                                                       @Header("userId") Integer userId);

    @GET("api/Supervisor/HouseScanifyDetailsGridRow")
    Call<List<EmployeeDetailsDTO>> getEmployeesDetailsUpdated(@Header("Content-Type") String contentType,
                                                              @Header("FromDate") String fromDate,
                                                              @Header("Todate") String toDate,
                                                              @Header("appId") String appId,
                                                              @Header("userId") Integer userId);

//qrEmpId


}
