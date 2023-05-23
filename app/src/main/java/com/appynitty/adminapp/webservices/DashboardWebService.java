package com.appynitty.adminapp.webservices;

import com.appynitty.adminapp.models.DashboardDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface DashboardWebService {
    @GET("api/Supervisor/AllUlb")
    Call<List<DashboardDTO>> getAll_ULBs(@Header("Content-Type") String content_type,
                                         @Header("EmpType") String emp_type,
                                         @Header("userId") String user_id,
                                         @Header("status") Boolean status);
}
