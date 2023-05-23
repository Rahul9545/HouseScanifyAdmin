package com.appynitty.adminapp.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.AttendanceDTO;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.AttendanceWebService;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceRepository {
    private static final String TAG = "AttendanceRepository";
    private static final AttendanceRepository instance = new AttendanceRepository();
    private final MutableLiveData<List<AttendanceDTO>> attendanceListLiveData = new MutableLiveData<>();

    public static AttendanceRepository getInstance() {
        return instance;
    }

    public void getListOfAttendance(String appId, IAttendanceResponse iAttendanceResponse) {
        Log.e(TAG, "getListOfAttendance: ");
        String empType = Prefs.getString(MainUtils.EMP_TYPE);
//        String userId = "";
        String userId = "0";
        String fromDate = MainUtils.getLocalDate();
        String toDate = MainUtils.getLocalDate();
        /*String appId = Prefs.getString(MainUtils.APP_ID);*/

        AttendanceWebService attendanceWebService = RetrofitClient.createService(AttendanceWebService.class, MainUtils.BASE_URL);
        Call<List<AttendanceDTO>> attendanceDTOCall = attendanceWebService.getFragAttendanceList(MainUtils.CONTENT_TYPE, empType, userId, appId,
                fromDate, toDate);
        attendanceDTOCall.enqueue(new Callback<List<AttendanceDTO>>() {
            @Override
            public void onResponse(Call<List<AttendanceDTO>> call, Response<List<AttendanceDTO>> response) {

                if (response.code() == 200) {
                    if (response.body() != null) {
                        /*attendanceListLiveData = (MutableLiveData<List<AttendanceDTO>>) response.body();*/
                        attendanceListLiveData.setValue(response.body());
                        iAttendanceResponse.onResponse(attendanceListLiveData);
                        Log.e(TAG, "onResponse: " + response.body());
                        Log.e(TAG, "iAttendanceResponse: " + iAttendanceResponse);
                    }
                } else if (response.code() == 500) {
                    Log.e(TAG, "onResponse: " + response.body());
                }

            }

            @Override
            public void onFailure(Call<List<AttendanceDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iAttendanceResponse.onFailure(t);
            }
        });
    }

    public void getFilteredEmpDetails(String frmDate, String toDate, String appId, String userId, IAttendanceResponse iAttendanceResponse) {
        String empType = Prefs.getString(MainUtils.EMP_TYPE);

        AttendanceWebService attendanceWebService = RetrofitClient.createService(AttendanceWebService.class, MainUtils.BASE_URL);
        Call<List<AttendanceDTO>> attendanceDTOCall = attendanceWebService.getFragAttendanceList(MainUtils.CONTENT_TYPE, empType, userId, appId, frmDate, toDate);

        attendanceDTOCall.enqueue(new Callback<List<AttendanceDTO>>() {
            @Override
            public void onResponse(Call<List<AttendanceDTO>> call, Response<List<AttendanceDTO>> response) {
                if (response.code() == 200) {
                    Log.e(TAG, "onResponse: empDetails:- " + response.body().toString());

                    attendanceListLiveData.setValue(response.body());
                    iAttendanceResponse.onResponse(attendanceListLiveData);
                }
            }

            @Override
            public void onFailure(Call<List<AttendanceDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iAttendanceResponse.onFailure(t);
            }
        });
    }

    public interface IAttendanceResponse {
        void onResponse(MutableLiveData<List<AttendanceDTO>> attendanceResponse);

        void onFailure(Throwable t);
    }
}
