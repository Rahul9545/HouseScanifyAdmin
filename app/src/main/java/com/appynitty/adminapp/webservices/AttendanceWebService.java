package com.appynitty.adminapp.webservices;

import com.appynitty.adminapp.models.AttendanceDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface AttendanceWebService {
    @GET("api/Supervisor/AttendanceGridRow")
    Call<List<AttendanceDTO>> getFragAttendanceList (@Header("Content-Type") String content_type,
                                                     @Header("EmpType") String EmpType,
                                                     @Header("qrEmpId") String userId,
                                                     @Header("appId") String appId,
                                                     @Header("FromDate") String fromDate,
                                                     @Header("Todate") String toDate);

}
