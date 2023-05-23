package com.appynitty.adminapp.webservices;

import com.appynitty.adminapp.models.UserLocationDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserLocationWebservice {
    @POST("api/Save/UserLocation")
    Call<List<UserLocationDTO>> sendLocation(@Header("appId") String appId,
                                             @Header("typeId") String typeId,
                                             @Header("batteryStatus") String batteryStatus,
                                             @Header("Content-Type") String ContentType,
                                             @Header("EmpType") String EmpType,
                                             @Body List<UserLocationDTO> locationReqBody);

    @POST("api/Save/UserLocation")
    Call<List<UserLocationDTO>> sendOfflineLocation(@Header("appId") String appId,
                                                    @Header("typeId") String typeId,
                                                    @Header("batteryStatus") String batteryStatus,
                                                    @Header("Content-Type") String ContentType,
                                                    @Header("EmpType") String EmpType,
                                                    @Body List<UserLocationDTO> locationReqBody);


}
