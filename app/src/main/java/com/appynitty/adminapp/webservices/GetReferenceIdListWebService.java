package com.appynitty.adminapp.webservices;

import com.appynitty.adminapp.models.ReferenceIdDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface GetReferenceIdListWebService {
    @GET("api/Supervisor/GetHouseList")
    Call<List<ReferenceIdDTO>> getReferenceIdList(@Header("appId") String appId,
                                                  @Header("ListType") String searchItem);
}
