package com.appynitty.adminapp.webservices;

import com.appynitty.adminapp.models.LoginResult;
import com.appynitty.adminapp.models.LoginUserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginWebService {
    @POST("api/Supervisor/Login")
    Call<LoginResult> saveLoginDetails(@Header("Content-Type") String content_type,
                                       @Header("EmpType") String emp_type,
                                       @Body LoginUserDTO loginUser);
}
