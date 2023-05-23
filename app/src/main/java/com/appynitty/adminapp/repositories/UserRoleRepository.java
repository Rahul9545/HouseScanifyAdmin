package com.appynitty.adminapp.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.UserRoleModelDTO;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.UserRoleListWebService;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRoleRepository {
    private static final String TAG = "UserRoleRepository";

    private static final UserRoleRepository instance = new UserRoleRepository();
    private final MutableLiveData<List<UserRoleModelDTO>> userRoleLiveData = new MutableLiveData<>();

    public static UserRoleRepository getInstance() {
        return instance;
    }

    public void getUserRoleActiveList(Boolean status, IUserRoleResponse iUserRoleResponse) {
        Log.e(TAG, "getEmpDList: ");
        String empType = Prefs.getString(MainUtils.EMP_TYPE);
        String userId = Prefs.getString(MainUtils.USER_ID);
        /*String empId = Prefs.getString(MainUtils.EMP_ID);*/
        String empId = "0";

        UserRoleListWebService userRoleListWebService = RetrofitClient.createService(UserRoleListWebService.class, MainUtils.BASE_URL);
        Call<List<UserRoleModelDTO>> userRoleDTOCall = userRoleListWebService.getUserRoleList(MainUtils.CONTENT_TYPE,
                empType, userId, empId, status);
        userRoleDTOCall.enqueue(new Callback<List<UserRoleModelDTO>>() {
            @Override
            public void onResponse(Call<List<UserRoleModelDTO>> call, Response<List<UserRoleModelDTO>> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {

                        userRoleLiveData.setValue(response.body());
                        iUserRoleResponse.onResponse(userRoleLiveData);
                        Log.e(TAG, "onResponse: " + response.body());
                        Log.e(TAG, "iAttendanceResponse: " + iUserRoleResponse);
                    }
                } else if (response.code() == 500) {
                    Log.e(TAG, "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<UserRoleModelDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iUserRoleResponse.onFailure(t);
            }
        });

    }

    public void getUserRoleInactiveList(Boolean status, IUserRoleResponse iUserRoleResponse) {
        Log.e(TAG, "getEmpDList: ");
        String empType = Prefs.getString(MainUtils.EMP_TYPE);
        String userId = Prefs.getString(MainUtils.USER_ID);
        /*String empId = Prefs.getString(MainUtils.EMP_ID);*/
        String empId = "0";

        UserRoleListWebService userRoleListWebService = RetrofitClient.createService(UserRoleListWebService.class, MainUtils.BASE_URL);
        Call<List<UserRoleModelDTO>> userRoleDTOCall = userRoleListWebService.getUserRoleList(MainUtils.CONTENT_TYPE,
                empType, userId, empId, status);
        userRoleDTOCall.enqueue(new Callback<List<UserRoleModelDTO>>() {
            @Override
            public void onResponse(Call<List<UserRoleModelDTO>> call, Response<List<UserRoleModelDTO>> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {

                        userRoleLiveData.setValue(response.body());
                        iUserRoleResponse.onResponse(userRoleLiveData);
                        Log.e(TAG, "onResponse: " + response.body());
                        Log.e(TAG, "iAttendanceResponse: " + iUserRoleResponse);
                    }
                } else if (response.code() == 500) {
                    Log.e(TAG, "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<UserRoleModelDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iUserRoleResponse.onFailure(t);
            }
        });

    }


    public interface IUserRoleResponse {
        void onResponse(MutableLiveData<List<UserRoleModelDTO>> userRoleResponse);

        void onFailure(Throwable t);
    }

}
