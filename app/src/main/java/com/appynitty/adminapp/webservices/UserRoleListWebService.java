package com.appynitty.adminapp.webservices;

import com.appynitty.adminapp.models.EmpDModelDTO;
import com.appynitty.adminapp.models.UserRoleModelDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserRoleListWebService {
    @GET("api/Supervisor/UserRoleList")
    Call<List<UserRoleModelDTO>> getUserRoleList(@Header("Content-Type") String contentType,
                                                 @Header("EmpType") String emp_type,
                                                 @Header("userId") String userId,
                                                 @Header("EmpId") String EmpId,
                                                 @Header("status") Boolean status);
}
