package com.appynitty.adminapp.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.DutyDTO;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.DutyOnOffWebservice;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DutyOnOffRepo {
    private static final String TAG = "DutyOnOffRepo";
    public static DutyOnOffRepo instance = new DutyOnOffRepo();
    MutableLiveData<DutyDTO> dutyMutableLiveData = new MutableLiveData<>();

    public static DutyOnOffRepo getInstance() {
        return instance;
    }

    public void setDuty(DutyDTO dutyParams, IDutyResponse iDutyResponse) {
        String empType = Prefs.getString(MainUtils.EMP_TYPE);
        String empId = Prefs.getString(MainUtils.EMP_ID);
        String batteryStatus = String.valueOf(MainUtils.getBatteryStatus());

        dutyParams.setEmployeeType(empType);
        dutyParams.setEmpId(empId);
        dutyParams.setBatteryStatus(batteryStatus);
        DutyOnOffWebservice dutyOnOffWebservice = RetrofitClient.createService(DutyOnOffWebservice.class, MainUtils.BASE_URL);
        Call<DutyDTO> dutyCall = null;
        if (!dutyParams.getStartLat().isEmpty()) {
            dutyCall = dutyOnOffWebservice.dutyPunchIn(dutyParams);
        } else if (!dutyParams.getEndLatitude().isEmpty()) {
            dutyCall = dutyOnOffWebservice.dutyPunchOut(dutyParams);
        }

        assert dutyCall != null;
        dutyCall.enqueue(new Callback<DutyDTO>() {
            @Override
            public void onResponse(Call<DutyDTO> call, Response<DutyDTO> response) {
                dutyMutableLiveData.setValue(response.body());
                iDutyResponse.onResponse(dutyMutableLiveData);
                Log.e(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<DutyDTO> call, Throwable t) {
                iDutyResponse.onFailure(t);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    public void checkAttendancefromServer(IDutyResponse iDutyResponse) {
        DutyDTO dutyParams = new DutyDTO();
        String empType = Prefs.getString(MainUtils.EMP_TYPE);
        String empId = Prefs.getString(MainUtils.EMP_ID);

        dutyParams.setEmpId(empId);
        dutyParams.setStartDate(MainUtils.getDate());
        DutyOnOffWebservice checkAttendanceService = RetrofitClient.createService(DutyOnOffWebservice.class, MainUtils.BASE_URL);
        Call<DutyDTO> checkAttendanceCall = checkAttendanceService.AttendenceCheck(dutyParams);
        checkAttendanceCall.enqueue(new Callback<DutyDTO>() {
            @Override
            public void onResponse(Call<DutyDTO> call, Response<DutyDTO> response) {
                dutyMutableLiveData.setValue(response.body());
                iDutyResponse.onResponse(dutyMutableLiveData);
                Log.e(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<DutyDTO> call, Throwable t) {
                iDutyResponse.onFailure(t);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }


    public interface IDutyResponse {
        public void onResponse(MutableLiveData<DutyDTO> dutyOnOffMutableLiveData);

        public void onFailure(Throwable t);
    }
}
