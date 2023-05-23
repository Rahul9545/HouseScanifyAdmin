package com.appynitty.adminapp.webservices;

import com.appynitty.adminapp.models.DutyDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DutyOnOffWebservice {
    @POST("/api/Supervisor/SupervisorAttendenceIn")
    Call<DutyDTO> dutyPunchIn(@Body DutyDTO dutyReqBody);

    @POST("/api/Supervisor/SupervisorAttendenceOut")
    Call<DutyDTO> dutyPunchOut(@Body DutyDTO dutyReqBody);

    @POST("api/Supervisor/SupervisorAttendenceCheck")
    Call<DutyDTO> AttendenceCheck(@Body DutyDTO dutyReqBody);
}
