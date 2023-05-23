package com.appynitty.adminapp.webservices;

import com.appynitty.adminapp.models.QrImageStatusDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UpdateQrImageStatusWebservice {
    //to change the approve reject status for houses
    @POST("api/Supervisor/UpdateQRstatus")
    Call<List<QrImageStatusDTO>> setQrImageStatus(@Header("Content-Type") String content_type,
                                                  @Header("appId") String appId,
                                                  @Body List<QrImageStatusDTO> qrImageRequestBody);

    //to change the approve reject status for dump-yard
    @POST("api/Supervisor/UpdateQRstatusDump")
    Call<List<QrImageStatusDTO>> setQrImageStatusDump(@Header("Content-Type") String content_type,
                                                      @Header("appId") String appId,
                                                      @Body List<QrImageStatusDTO> qrImageRequestBody);

    //to change the approve reject status for Liquid waste
    @POST("api/Supervisor/UpdateQRstatusLiquid")
    Call<List<QrImageStatusDTO>> setQrImageStatusLiquid(@Header("Content-Type") String content_type,
                                                        @Header("appId") String appId,
                                                        @Body List<QrImageStatusDTO> qrImageRequestBody);

    //to change the approve reject status for Street waste
    @POST("api/Supervisor/UpdateQRstatusStreet")
    Call<List<QrImageStatusDTO>> setQrImageStatusStreet(@Header("Content-Type") String content_type,
                                                        @Header("appId") String appId,
                                                        @Body List<QrImageStatusDTO> qrImageRequestBody);

}
