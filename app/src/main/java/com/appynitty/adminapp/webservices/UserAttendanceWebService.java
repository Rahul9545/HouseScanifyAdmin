package com.appynitty.adminapp.webservices;

import com.appynitty.adminapp.models.AttendanceDTO;
import com.appynitty.adminapp.models.UserAttendanceDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserAttendanceWebService {
    @GET("api/Supervisor/GetUserRoleAttendance")
    Call<List<UserAttendanceDTO>> getUserAttendanceMnPList (@Header("Content-Type") String content_type,
                                                         @Header("userId") String userId,
                                                         @Header("FromDate") String fromDate,
                                                         @Header("Todate") String toDate,
                                                         @Header("IsMobile") boolean isMobile);
}

