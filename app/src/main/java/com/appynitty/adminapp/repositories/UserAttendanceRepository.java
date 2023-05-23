package com.appynitty.adminapp.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.UserAttendanceDTO;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.UserAttendanceWebService;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAttendanceRepository {
    private static final String TAG = "UserAttendanceRepository";
    private static final UserAttendanceRepository instance = new UserAttendanceRepository();
    private final MutableLiveData<List<UserAttendanceDTO>> userAttendanceMobileListLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<UserAttendanceDTO>> userAttendancePcListLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<UserAttendanceDTO>> userAttendanceFilterListLiveData = new MutableLiveData<>();

    public static UserAttendanceRepository getInstance() {
        return instance;
    }

    public void getUserAttendanceMobileList(boolean isMobile, IUserAttendanceResponse iUserAttendanceResponse){
        Log.e(TAG, "getUserAttendanceMobileList: ");
        String userId = "0";
        String fromDate = MainUtils.getLocalDate();
        String toDate = MainUtils.getLocalDate();

        UserAttendanceWebService userAttendanceMobile = RetrofitClient.createService(UserAttendanceWebService.class, MainUtils.BASE_URL);
        Call<List<UserAttendanceDTO>> userAttendanceDTOMobile = userAttendanceMobile.getUserAttendanceMnPList(MainUtils.CONTENT_TYPE,userId,fromDate,toDate,isMobile);

        userAttendanceDTOMobile.enqueue(new Callback<List<UserAttendanceDTO>>() {
            @Override
            public void onResponse(Call<List<UserAttendanceDTO>> call, Response<List<UserAttendanceDTO>> response) {
                if (response.code() == 200){
                    Log.e(TAG, "onResponse: " + response.body());
                    userAttendanceMobileListLiveData.setValue(response.body());
                    iUserAttendanceResponse.onResponse(userAttendanceMobileListLiveData);
                    Log.e(TAG, "onResponse: " + response.body());
                    Log.e(TAG, "iUserAttendanceResponse: " + iUserAttendanceResponse);

                }else if (response.code() == 500){
                    Log.e(TAG, "onResponse: " + response.body());

                }
            }

            @Override
            public void onFailure(Call<List<UserAttendanceDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    public void getUserAttendancePcList(boolean isMobile, IUserAttendanceResponse iUserAttendanceResponse){
        Log.e(TAG, "getUserAttendanceMobileList: ");
        String userId = "0";
        String fromDate = MainUtils.getLocalDate();
        String toDate = MainUtils.getLocalDate();

        UserAttendanceWebService userAttendancePc = RetrofitClient.createService(UserAttendanceWebService.class, MainUtils.BASE_URL);
        Call<List<UserAttendanceDTO>> userAttendanceDTOMobile = userAttendancePc.getUserAttendanceMnPList(MainUtils.CONTENT_TYPE,userId,fromDate,toDate,isMobile);

        userAttendanceDTOMobile.enqueue(new Callback<List<UserAttendanceDTO>>() {
            @Override
            public void onResponse(Call<List<UserAttendanceDTO>> call, Response<List<UserAttendanceDTO>> response) {

                if (response.code() == 200){
                    Log.e(TAG, "onResponse: " + response.body());
                    userAttendancePcListLiveData.setValue(response.body());
                    iUserAttendanceResponse.onResponse(userAttendancePcListLiveData);
                    Log.e(TAG, "onResponse: " + response.body());
                    Log.e(TAG, "iUserAttendanceResponse: " + iUserAttendanceResponse);

                }else if (response.code() == 500){
                    Log.e(TAG, "onResponse: " + response.body());

                }
            }

            @Override
            public void onFailure(Call<List<UserAttendanceDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    public void getUserAttendanceFilter(String fromDate, String toDate, String userId, boolean isMobile, IUserAttendanceResponse iUserAttendanceResponse){

        UserAttendanceWebService userAttendanceFilter = RetrofitClient.createService(UserAttendanceWebService.class,MainUtils.BASE_URL);
        Call<List<UserAttendanceDTO>> userAttendanceDTOFilter = userAttendanceFilter.getUserAttendanceMnPList(MainUtils.CONTENT_TYPE, userId,fromDate,toDate,isMobile);

        userAttendanceDTOFilter.enqueue(new Callback<List<UserAttendanceDTO>>() {
            @Override
            public void onResponse(Call<List<UserAttendanceDTO>> call, Response<List<UserAttendanceDTO>> response) {

                if (response.code() == 200){
                    Log.e(TAG, "onResponse: " + response.body());
                    userAttendanceFilterListLiveData.setValue(response.body());
                    iUserAttendanceResponse.onResponse(userAttendanceFilterListLiveData);
                    Log.e(TAG, "onResponse: " + response.body());
                    Log.e(TAG, "iUserAttendanceResponse: " + iUserAttendanceResponse);

                }else if (response.code() == 500){
                    Log.e(TAG, "onResponse: " + response.body());

                }
            }

            @Override
            public void onFailure(Call<List<UserAttendanceDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }


    public interface IUserAttendanceResponse{
        void onResponse(MutableLiveData<List<UserAttendanceDTO>> userAttendanceResponse);
        void onFailure(Throwable throwable);
    }
}
