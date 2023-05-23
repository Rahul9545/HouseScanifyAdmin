package com.appynitty.adminapp.webservices;

import com.appynitty.adminapp.models.HouseDetailsImageDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface QRImageDataWebservice {
    @GET("api/Supervisor/HouseDetails")
    Call<List<HouseDetailsImageDTO>> getHouseQrImages(@Header("Content-Type") String contentType,
                                                      @Header("FromDate") String frmDate,
                                                      @Header("Todate") String toDate,
                                                      @Header("appId") String appId,
                                                      @Header("userId") String userId,
                                                      @Header("ReferanceId") String refId);


    @GET("api/Supervisor/DumpYardDetails")
    Call<List<HouseDetailsImageDTO>> getDumpYardQrImages(@Header("Content-Type") String contentType,
                                                         @Header("FromDate") String frmDate,
                                                         @Header("Todate") String toDate,
                                                         @Header("appId") String appId,
                                                         @Header("userId") String userId,
                                                         @Header("ReferanceId") String refId);


    @GET("api/Supervisor/LiquidDetails")
    Call<List<HouseDetailsImageDTO>> getLiquidDetailsQrImages(@Header("Content-Type") String contentType,
                                                              @Header("FromDate") String frmDate,
                                                              @Header("Todate") String toDate,
                                                              @Header("appId") String appId,
                                                              @Header("userId") String userId,
                                                              @Header("ReferanceId") String refId);

    @GET("api/Supervisor/StreetDetails")
    Call<List<HouseDetailsImageDTO>> getStreetDetailsQrImages(@Header("Content-Type") String contentType,
                                                              @Header("FromDate") String frmDate,
                                                              @Header("Todate") String toDate,
                                                              @Header("appId") String appId,
                                                              @Header("userId") String userId,
                                                              @Header("ReferanceId") String refId);


}
