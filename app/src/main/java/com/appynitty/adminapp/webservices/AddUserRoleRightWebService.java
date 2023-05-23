package com.appynitty.adminapp.webservices;

import com.appynitty.adminapp.models.AddEmpResult;
import com.appynitty.adminapp.models.AddUserRoleRightDTO;
import com.appynitty.adminapp.models.AddUserRoleRightResult;
import com.appynitty.adminapp.models.UserRoleModelDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AddUserRoleRightWebService {
    @POST("api/Supervisor/AddHouseScanifyUserRole")
    Call<List<AddUserRoleRightResult>> addNewUserRoleRightHS(@Header("Content-Type") String content_type,
                                                       @Body List<AddUserRoleRightDTO> addUserRoleRightDTOS);

    @POST("api/Supervisor/AddHouseScanifyUserRole")
    Call<List<AddUserRoleRightResult>> updateUserRoleRightHS(@Header("Content-Type") String content_type,
                                                   @Body List<UserRoleModelDTO> userRoleModelDTOS);
}
